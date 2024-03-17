from django.db import models

# Create your models here.
class TravelStates(models.Model):
  class Meta:
    ordering = ['name']
    verbose_name = 'Состояние поездки'
    verbose_name_plural = 'Состояния поездки'

  name = models.CharField(max_length=20, unique=True)

class Place(models.Model):
  class Meta:
    ordering = ['city', 'address']
    verbose_name = 'Место'
    verbose_name_plural = 'Места'

  name    = models.CharField(max_length=255)
  city    = models.CharField(max_length=255)
  address = models.TextField()
  phones  = models.CharField(max_length=255)

class Travel(models.Model):
  class Meta:
    ordering = ['-datetime_start', '-datetime_end']
    unique_together = ['datetime_start', 'datetime_end']
    verbose_name = 'Путешествие'
    verbose_name_plural = 'Путешествия'

  name           = models.CharField(max_length=255, unique=True)
  datetime_start = models.DateTimeField()
  datetime_end   = models.DateTimeField()
  points         = models.ManyToManyField(Place)
  participants   = models.CharField(max_length=255)
  state          = models.ForeignKey(TravelStates, on_delete=models.PROTECT)

