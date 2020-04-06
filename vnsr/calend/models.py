from django.db import models

from calend import constants

# Create your models here.
class Marks(models.Model):
  class Meta:
    verbose_name = 'Отметка'
    verbose_name_plural = 'Отметки'
    ordering = ['date', 'sign', 'comment']
    unique_together = ['date', 'sign', 'comment']

  date = models.DateField()
  sign = models.ForeignKey('Signs', on_delete=models.SET_NULL, null=True)
  comment = models.CharField(max_length=50, null=True)

class Productions(models.Model):
  class Meta:
    verbose_name = 'Тип'
    verbose_name_plural = 'Типы'
    ordering = ['date']

  date = models.DateField(unique=True)
  type = models.CharField(max_length=1, choices=constants.DAY_TYPES, db_index=True)
  comment = models.CharField(max_length=50, null=True)

class Signs(models.Model):
  class Meta:
    ordering = ['name']
    verbose_name = 'Признак'
    verbose_name_plural = 'Признаки'

  name = models.CharField(max_length=50, unique=True)

