from django.db import models

from kladr.models import Address

# Create your models here.
class FuelStation(models.Model):
  class Meta:
    verbose_name = 'АЗС'
    verbose_name_plural = 'АЗСы'

  company = models.CharField(max_length=255, verbose_name='Организация')
  name    = models.CharField(max_length=20,  verbose_name='Название')
  phone   = models.CharField(max_length=20,  verbose_name='Телефон')
  address = models.ForeignKey(Address, on_delete=models.PROTECT, verbose_name='Адрес')

  def __str__(self):
    return f'{self.name} - {self.address.name}'

class Fuel(models.Model):
  class Meta:
    verbose_name = 'Топливо'
    verbose_name_plural = 'Топливо'

  name = models.CharField(max_length=50, verbose_name='Название')
  fuel_station = models.ForeignKey(FuelStation, on_delete=models.PROTECT, related_name='fuels', verbose_name='АЗС')

  def __str__(self):
    return f'{self.name} - {self.fuel_station.name}'

class Refuel(models.Model):
  class Meta:
    verbose_name = 'Дозаправка'
    verbose_name_plural = 'Дозаправки'

  fuel_station         = models.ForeignKey(FuelStation, on_delete=models.PROTECT, related_name='refuels', verbose_name='АЗС')
  check_number         = models.PositiveSmallIntegerField(null=True, blank=True, verbose_name='Номер чека')
  datetime             = models.DateTimeField(verbose_name='Дата и время')
  trk                  = models.PositiveSmallIntegerField(default=3, verbose_name='ТРК №')
  fuel                 = models.ForeignKey(Fuel, on_delete=models.PROTECT, verbose_name='Топливо')
  count                = models.DecimalField(max_digits=4, decimal_places=2, verbose_name='Количество')
  price                = models.DecimalField(max_digits=5, decimal_places=2, verbose_name='Цена')
  cost                 = models.DecimalField(max_digits=7, decimal_places=2, verbose_name='Стоимость')
  distance_reserve     = models.PositiveSmallIntegerField(default=500, verbose_name='Запас хода')
  fuel_consumption_avg = models.DecimalField(default=10, max_digits=3, decimal_places=1, verbose_name='Расход топлива общий')
  odometer             = models.PositiveIntegerField(verbose_name='Общий пробег')
  distance             = models.DecimalField(max_digits=4, decimal_places=1, verbose_name='Межзаправочный пробег')
  fuel_consumption     = models.DecimalField(default=10,max_digits=4, decimal_places=1, verbose_name='Межзаправочный расход топлива')
  timedelta            = models.CharField(max_length=5, verbose_name='Временной интервал')

  def __str__(self):
    return str(self.datetime)

