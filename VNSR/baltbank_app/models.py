from django.db import models

# Create your models here.
class Shedule (models.Model):
  '''График работы'''
  class Meta ():
    db_table = 'shedule'

  data        = models.DateField    (primary_key = True,            verbose_name = 'Дата')
  shift       = models.ForeignKey   ('WorkPlane', db_column = 'shift')
  is_vacation = models.BooleanField (default = False, null = False, verbose_name = 'Признак отпуска')
  is_sick     = models.BooleanField (default = False, null = False, verbose_name = 'Призак больничного')

class WorkPlane (models.Model):
  '''План смен'''
  class Meta ():
    db_table = 'work_plane'

  code  = models.CharField    (max_length = 5, primary_key = True)
  start = models.TimeField    (verbose_name = 'Начало смены')
  end   = models.TimeField    (verbose_name = 'Окончание смены')
  hour  = models.IntegerField (verbose_name = 'Рабочие часы')
