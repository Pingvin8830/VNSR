from django.db import models

# Create your models here.
class TravelStates(models.Model):
  class Meta:
    ordering = ['name']
    verbose_name = 'Состояние поездки'
    verbose_name_plural = 'Состояния поездки'

  name = models.CharField(max_length=20, unique=True, verbose_name='Название')

class Place(models.Model):
  class Meta:
    ordering = ['city', 'address']
    verbose_name = 'Место'
    verbose_name_plural = 'Места'

  name    = models.CharField(max_length=255, verbose_name='Название')
  city    = models.CharField(max_length=255, verbose_name='Город')
  address = models.TextField(verbose_name='Адрес')
  phones  = models.CharField(max_length=255, verbose_name='Телефоны')

class Travel(models.Model):
  class Meta:
    ordering = ['-datetime_start', '-datetime_end']
    unique_together = ['datetime_start', 'datetime_end']
    verbose_name = 'Путешествие'
    verbose_name_plural = 'Путешествия'

  name           = models.CharField(max_length=255, unique=True, verbose_name='Название')
  datetime_start = models.DateTimeField(verbose_name='Дата и время начала')
  datetime_end   = models.DateTimeField(verbose_name='Дата и время окончания')
  points         = models.ManyToManyField(Place, blank=True, verbose_name='Путевые точки')
  participants   = models.CharField(max_length=255, verbose_name='Участники')
  state          = models.ForeignKey(TravelStates, on_delete=models.PROTECT, verbose_name='Состояние')

