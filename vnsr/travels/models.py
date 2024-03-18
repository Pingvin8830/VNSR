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

  name           = models.CharField(max_length=255, unique=True, verbose_name='Название')
  datetime_start = models.DateTimeField(verbose_name='Дата и время начала')
  datetime_end   = models.DateTimeField(verbose_name='Дата и время окончания')
  participants   = models.CharField(max_length=255, verbose_name='Участники')
  state          = models.ForeignKey(TravelState, on_delete=models.PROTECT, verbose_name='Состояние')

  def __str__(self):
    return self.name

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

