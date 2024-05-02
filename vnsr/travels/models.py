from decimal import Decimal
from django.db import models
from django.contrib import admin

from kladr.models import Address

import datetime

# Create your models here.
#class TravelState(models.Model):
#  class Meta:
#    ordering = ['name']
#    verbose_name = 'Состояние поездки'
#    verbose_name_plural = 'Состояния поездки'
#
#  name = models.CharField(max_length=20, unique=True, verbose_name='Название')
#
#  def __str__(self):
#    return self.name
#
#  def load(self, data):
#    self.name = data['name']
#
#  def to_json(self):
#    return {
#      'id': self.id,
#      'name': self.name
#    }

class Travel(models.Model):
  STATES = (
    ('U', 'Неизвестно'),
    ('P', 'Планирование'),
    ('R', 'Выполнение'),
    ('S', 'Завершена'),
    ('C', 'Отменена')
  )

  class Meta:
    verbose_name = 'Путешествие'
    verbose_name_plural = 'Путешествия'

  name             = models.CharField(max_length=255, unique=True, verbose_name='Название')
  participants     = models.CharField(max_length=255, verbose_name='Участники')
#  state            = models.ForeignKey(TravelState, on_delete=models.PROTECT, verbose_name='Состояние')
  state            = models.CharField(max_length=1, choices=STATES, default='U', verbose_name='Состояние')
  fuel_consumption = models.DecimalField(default=10, max_digits=3, decimal_places=1, verbose_name='Расход топлива')
  fuel_price       = models.DecimalField(max_digits=5, decimal_places=2, verbose_name='Цена топлива')
  start_datetime   = models.DateTimeField(verbose_name='Дата и время начала')
  end_datetime     = models.DateTimeField(verbose_name='Дата и вемя окончания')

  def __str__(self):
    return self.name

#  def get_datetimes(self):
#    try: start = self.points.order_by('datetime')[0].datetime
#    except IndexError: start = None
#    try: end = self.points.order_by('-datetime')[0].datetime
#    except IndexError: end = None
#    return {'start': start, 'end': end}

#  @admin.display(
#    description='Начало',
#  )
#  def get_datetime_start(self):
#    return self.get_datetimes()['start']

#  @admin.display(
#    description='Окончание',
#  )
#  def get_datetime_end(self):
#    return self.get_datetimes()['end']

#  def get_ways(self):
#    return Way.objects.filter(start_point__travel__pk=self.pk)

  def get_distance(self):
    distance = 0
#    for way in self.get_ways():
#      distance += way.distance
    return distance

  def get_fuel_count(self):
    count = 0
#    for way in self.get_ways():
#      count += way.get_fuel_count()
    return count

  def get_fuel_cost(self):
    cost = 0
#    for way in self.get_ways():
#      cost += way.get_fuel_cost()
    return cost

  def get_toll_road_cost(self):
    cost = 0
    for road in self.toll_roads.all():
      cost += road.price
    return cost

  def get_hotel_cost(self):
    cost = 0
    for hotel in self.hotels.all():
      cost += hotel.cost
    return cost

  def get_cost(self):
    return self.get_fuel_cost() + self.get_toll_road_cost() + self.get_hotel_cost()

  def load(self, data):
    self.name = data['name']
    self.participants = data['participants']
    self.state = data['travel_state_code']
    self.fuel_consumption = data['fuel_consumption']
    self.fuel_price = data['fuel_price']
    self.start_datetime = datetime.datetime(
      data['start_datetime']['year'],
      data['start_datetime']['month'],
      data['start_datetime']['day'],
      data['start_datetime']['hour'],
      data['start_datetime']['minute'],
      0
    )
    self.end_datetime = datetime.datetime(
      data['end_datetime']['year'],
      data['end_datetime']['month'],
      data['end_datetime']['day'],
      data['end_datetime']['hour'],
      data['end_datetime']['minute'],
      0
    )

  def to_json(self):
    return {
      'object': 'Travel',
      'id': self.id,
      'name': self.name,
      'participants': self.participants,
      'travel_state_code': self.state,
      'fuel_consumption': self.fuel_consumption,
      'fuel_price': self.fuel_price,
      'start_datetime': {
        'year': self.start_datetime.year,
        'month': self.start_datetime.month,
        'day': self.start_datetime.day,
        'hour': self.start_datetime.hour,
        'minute': self.start_datetime.minute
      },
      'end_datetime': {
        'year': self.end_datetime.year,
        'month': self.end_datetime.month,
        'day': self.end_datetime.day,
        'hour': self.end_datetime.hour,
        'minute': self.end_datetime.minute
      }
    }

class Point(models.Model):
  DOINGS = (
    ('U', 'Неизвестно'),
    ('N', 'Ночёвка'),
    ('S', 'Перерыв'),
    ('R', 'Заправка')
  )

  class Meta:
#    ordering = ['datetime']
    verbose_name = 'Путевая точка'
    verbose_name_plural = 'Путевые точки'
    unique_together = ['arrival_datetime', 'departure_datetime']

#  travel   = models.ForeignKey(Travel, on_delete=models.PROTECT, related_name='points', verbose_name='Путешествие')
  address  = models.ForeignKey(Address, on_delete=models.PROTECT, verbose_name='Место')
#  datetime = models.DateTimeField(verbose_name='Дата и время')
#  doing    = models.CharField(max_length=10, db_index=True, verbose_name='Действие')
  arrival_datetime = models.DateTimeField(null=True, blank=True, verbose_name='Дата и время прибытия')
  departure_datetime = models.DateTimeField(null=True, blank=True, verbose_name='Дата и время отъезда')
  doing    = models.CharField(max_length=1, choices=DOINGS, default='U', verbose_name='Действие')
  odometer = models.PositiveIntegerField()

  def __str__(self):
    return f'{self.travel.name}, {self.address.name}, {self.doing}'

  def load(self, data):
#    correct_datetime = data['datetime'][6:10] + '-' + data['datetime'][3:5] + '-' + data['datetime'][:2] + ' ' + data['datetime'][11:16]
#    self.travel = Travel.objects.get(name=data['travel']['name'])
    self.address = Address.objects.get(name=data['address']['name'])
#    self.datetime = correct_datetime
    try:
      self.arrival_datetime = datetime.datetime(
        data['arrival_datetime']['year'],
        data['arrival_datetime']['month'],
        data['arrival_datetime']['day'],
        data['arrival_datetime']['hour'],
        data['arrival_datetime']['minute'],
        0
      )
    except KeyError: self.arrival_datetime = None
    try:
      self.departure_datetime = datetime.datetime(
        data['departure_datetime']['year'],
        data['departure_datetime']['month'],
        data['departure_datetime']['day'],
        data['departure_datetime']['hour'],
        data['departure_datetime']['minute'],
        0
      )
    except KeyError: self.departure_datetime = None
    self.doing = data['doing']
    self.odometer = data['odometer']

  def to_json(self):
    try: correct_arrival_msk = self.arrival_datetime + datetime.timedelta(hours=3)
    except TypeError: correct_arrival_msk = None
    try: correct_departure_msk = self.departure_datetime + datetime.timedelta(hours=3)
    except TypeError: correct_departure_msk = None
    res = {
      'object': 'Point',
      'id': self.id,
      'address': {
        'name': self.address.name,
      },
      'doing_code': self.doing,
      'odometer': self.odometer
    }
    try:
      res['arrival_datetime'] = {
        'year':   correct_arrival_msk.year,
        'month':  correct_arrival_msk.month,
        'day':    correct_arrival_msk.day,
        'hour':   correct_arrival_msk.hour,
        'minute': correct_arrival_msk.minute,
      }
    except AttributeError: res['arrival_datetime'] = None
    try:
      res['departure_datetime'] = {
        'year':   correct_departure_msk.year,
        'month':  correct_departure_msk.month,
        'day':    correct_departure_msk.day,
        'hour':   correct_departure_msk.hour,
        'minute': correct_departure_msk.minute,
      }
    except AttributeError: res['departure_datetime'] = None
    return res

class Way(models.Model):
  class Meta:
    verbose_name = 'Отрезок пути'
    verbose_name_plural = 'Отрезки пути'

#  travel       = models.ForeignKey(Travel, on_delete=models.PROTECT, related_name='ways', verbose_name='Расстояние')
  start_point  = models.ForeignKey(Point, on_delete=models.PROTECT, related_name='start_points', verbose_name='Старт')
  target_point = models.ForeignKey(Point, on_delete=models.PROTECT, related_name='target_points', verbose_name='Цель')
  distance     = models.DecimalField(max_digits=5, decimal_places=1, verbose_name='Расстояние')

  def __str__(self):
    return f'{self.start_point.place.name} - {self.target_point.place.name}'

  def get_fuel_count(self):
    return self.distance / 100 * self.start_point.travel.fuel_consumption

  def get_fuel_cost(self):
    return self.get_fuel_count() * self.start_point.travel.fuel_price

  def load(self, data):
    self.travel = Travel.objects.get(name=data['travel']['name'])
    self.start_point = Point.objects.get(travel__name=self.travel.name, address__name=data['start_point']['address']['name'])
    self.target_point = Point.objects.get(travel__name=self.travel.name, address__name=data['target_point']['address']['name'])
    self.distance = data['distance']

  def to_json(self):
    return {
      'object': 'Way',
      'id': self.id,
      'travel': {
        'name': self.travel.name,
      },
      'start_point': {
        'address': {
          'name': self.start_point.address.name,
        },
      },
      'target_point': {
        'address': {
          'name': self.target_point.address.name,
        },
      },
      'distance': self.distance
    }

class TollRoad(models.Model):
  class Meta:
    verbose_name = 'Платная дорога'
    verbose_name_plural = 'Платные дороги'

  travel = models.ForeignKey(Travel, on_delete=models.PROTECT, related_name='toll_roads', verbose_name='Путешествие')
  name  = models.CharField(max_length=10, verbose_name='Название')
  start = models.CharField(max_length=255, verbose_name='Начало')
  end   = models.CharField(max_length=255, verbose_name='Конец')
  price = models.DecimalField(max_digits=6, decimal_places=2, verbose_name='Цена')

  def __str__(self):
    return f'{self.start} - {self.end}'

class Hotel(models.Model):
  class Meta:
    verbose_name = 'Гостиница'
    verbose_name_plural = 'Гостиницы'

  travel    = models.ForeignKey(Travel, on_delete=models.PROTECT, related_name='hotels', verbose_name='Путешествие')
  address   = models.ForeignKey(Address, on_delete=models.PROTECT, verbose_name='Адрес')
  arrival   = models.DateTimeField(verbose_name='Заезд')
  departure = models.DateTimeField(verbose_name='Отъезд')
  cost      = models.DecimalField(max_digits=8, decimal_places=2, verbose_name='Стоимость')
  state     = models.TextField(verbose_name='Статус')

  def __str__(self):
    return f'{self.address.city} - {self.address.name}'

