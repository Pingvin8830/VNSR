from decimal import Decimal
from django.db import models

# Create your models here.
class TravelState(models.Model):
  class Meta:
    ordering = ['name']
    verbose_name = 'Состояние поездки'
    verbose_name_plural = 'Состояния поездки'

  name = models.CharField(max_length=20, unique=True, verbose_name='Название')

  def __str__(self):
    return self.name

class Place(models.Model):
  class Meta:
    ordering = ['city', 'address']
    verbose_name = 'Место'
    verbose_name_plural = 'Места'

  name    = models.CharField(max_length=255, verbose_name='Название')
  city    = models.CharField(max_length=255, verbose_name='Город')
  address = models.TextField(verbose_name='Адрес')
  phones  = models.CharField(max_length=255, verbose_name='Телефоны')

  def __str__(self):
    return self.name

class Travel(models.Model):
  class Meta:
    ordering = ['-datetime_start', '-datetime_end']
    unique_together = ['datetime_start', 'datetime_end']
    verbose_name = 'Путешествие'
    verbose_name_plural = 'Путешествия'

  name             = models.CharField(max_length=255, unique=True, verbose_name='Название')
  datetime_start   = models.DateTimeField(verbose_name='Дата и время начала')
  datetime_end     = models.DateTimeField(verbose_name='Дата и время окончания')
  participants     = models.CharField(max_length=255, verbose_name='Участники')
  state            = models.ForeignKey(TravelState, on_delete=models.PROTECT, verbose_name='Состояние')
  fuel_consumption = models.DecimalField(default=10, max_digits=3, decimal_places=1, verbose_name='Расход топлива')
  fuel_price       = models.DecimalField(max_digits=5, decimal_places=2, verbose_name='Цена топлива')

  def __str__(self):
    return self.name

  def get_ways(self):
    return Way.objects.filter(start_point__travel__pk=self.pk)

  def get_distance(self):
    distance = 0
    for way in self.get_ways():
      distance += way.distance
    return distance

  def get_fuel_count(self):
    count = 0
    for way in self.get_ways():
      count += way.get_fuel_count()
    return count

  def get_fuel_cost(self):
    cost = 0
    for way in self.get_ways():
      cost += way.get_fuel_cost()
    return cost

class Point(models.Model):
  class Meta:
    ordering = ['datetime']
    verbose_name = 'Путевая точка'
    verbose_name_plural = 'Путевые точки'

  travel   = models.ForeignKey(Travel, on_delete=models.PROTECT, related_name='points', verbose_name='Путешествие')
  place    = models.ForeignKey(Place, on_delete=models.PROTECT, verbose_name='Место')
  datetime = models.DateTimeField(verbose_name='Дата и время')
  doing    = models.CharField(max_length=10, db_index=True, verbose_name='Действие')

  def __str__(self):
    return f'{self.travel.name}, {self.place.name}, {self.doing}'

class Way(models.Model):
  class Meta:
    verbose_name = 'Отрезок пути'
    verbose_name_plural = 'Отрезки пути'

  start_point  = models.ForeignKey(Point, on_delete=models.PROTECT, related_name='start_points', verbose_name='Старт')
  target_point = models.ForeignKey(Point, on_delete=models.PROTECT, related_name='target_points', verbose_name='Цель')
  distance     = models.DecimalField(max_digits=5, decimal_places=1, verbose_name='Расстояние')

  def __str__(self):
    return f'{self.start_point.place.name} - {self.target_point.place.name}'

  def get_fuel_count(self):
    return self.distance / 100 * self.start_point.travel.fuel_consumption

  def get_fuel_cost(self):
    return self.get_fuel_count() * self.start_point.travel.fuel_price

