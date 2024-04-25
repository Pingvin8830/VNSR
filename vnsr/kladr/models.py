from django.db import models

# Create your models here.
class Region(models.Model):
  class Meta:
    verbose_name = 'Регион'
    verbose_name_plural = 'Регионы'
    ordering = ['code']

  code = models.CharField(max_length=2, unique=True, verbose_name='Код')
  name = models.CharField(max_length=255, verbose_name='Название')

  def __str__(self):
    return f'{self.code} - {self.name}'

  def load(self, data):
    self.code = data['code']
    self.name = data['name']

  def to_json(self):
    return {
      'id': self.id,
      'code': self.code,
      'name': self.name
    }

class CityType(models.Model):
  class Meta:
    verbose_name = 'Тип населённого пункта'
    verbose_name_plural = 'Типы населённых пунктов'

  name = models.CharField(max_length=255, unique=True, verbose_name='Название')
  short = models.CharField(max_length=5, verbose_name='Сокращённое название')

  def __str__(self):
    return self.name

  def load(self, data):
    self.name = data['name']
    self.short = data['short']

  def to_json(self):
    return {
      'id': self.id,
      'name': self.name,
      'short': self.short
    }

class City(models.Model):
  class Meta:
    verbose_name = 'Населённый пункт'
    verbose_name_plural = 'Населённые пункты'

  name   = models.CharField(max_length=255, unique=True, verbose_name='Название')
  type   = models.ForeignKey(CityType, on_delete=models.PROTECT, related_name='cityes', verbose_name='Тип')

  def __str__(self):
    return f'{self.type.short} {self.name}'

  def load(self, data):
    self.name = data['name']
    self.type = CityType.objects.get(name=data['type_name'])

  def to_json(self):
    return {
      'id': self.id,
      'name': self.name,
      'type_name': self.type.name
    }

class StreetType(models.Model):
  class Meta:
    verbose_name = 'Тип улицы'
    verbose_name_plural = 'Типы улиц'

  name = models.CharField(max_length=20, unique=True, verbose_name='Название')
  short = models.CharField(max_length=10, verbose_name='Сокращённое название')

  def __str__(self):
    return self.name

  def load(self, data):
    self.name = data['name']
    self.short = data['short']

  def to_json(self):
    return {
      'id': self.id,
      'name': self.name,
      'short': self.short
    }

class Street(models.Model):
  class Meta:
    verbose_name = 'Улица'
    verbose_name_plural = 'Улицы'
    unique_together = ['type', 'name']

  type = models.ForeignKey(StreetType, on_delete=models.PROTECT, related_name='streets', verbose_name='Тип')
  name = models.CharField(max_length=25, verbose_name='Название')

  def __str__(self):
    return f'{self.type.short} {self.name}'

  def load(self, data):
    self.name = data['name']
    self.type = StreetType.objects.get(name=data['type_name'])

  def to_json(self):
    return {
      'id': self.id,
      'name': self.name,
      'type_name': self.type.name
    }

class Address(models.Model):
  class Meta:
    verbose_name = 'Адрес'
    verbose_name_plural = 'Адреса'

  name     = models.CharField(max_length=20, verbose_name='Название')
  region   = models.ForeignKey(Region, on_delete=models.PROTECT, related_name='addresses', verbose_name='Регион')
  city     = models.ForeignKey(City, on_delete=models.PROTECT, related_name='addresses', verbose_name='Населённый пункт')
  street   = models.ForeignKey(Street, on_delete=models.PROTECT, related_name='addresses', verbose_name='Улица')
  house    = models.CharField(max_length=10, verbose_name='Номер дома')
  building = models.CharField(max_length=5, null=True, blank=True, verbose_name='Строение/корпус/литера')
  flat     = models.PositiveSmallIntegerField(null=True, blank=True, verbose_name='Квартира')

  def __str__(self):
    return self.name

  def short(self):
    ret = f'{self.street}, {self.house}'
    if self.building: ret += f'{self.building}'
    if self.flat: ret += f', {self.flat}'
    return ret

  def load(self, data):
    self.name = data['name']
    self.region = Region.objects.get(code=data['region_code'])
    self.city = City.objects.get(name=data['city_name'])
    self.street = Street.objects.get(name=data['street_name'], type__name=data['street_type_name'])
    self.house = data['house']
    self.building = data['building']
    self.flat = data['flat']

  def to_json(self):
    return {
      'id': self.id,
      'name': self.name,
      'region_code': self.region.code,
      'city_name': self.city.name,
      'street_name': self.street.name,
      'street_type_name': self.street.type.name,
      'house': self.house,
      'building': self.building,
      'flat': self.flat
    }

