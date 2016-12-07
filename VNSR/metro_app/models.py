from django.db import models

# Create your models here.

class WorkPlane (models.Model):
  '''План смен'''
  class Meta ():
    db_table        = 'work_plane'
    unique_together = (('date_start', 'code'))

  id          = models.AutoField (primary_key = True)
  date_start  = models.DateField ()
  date_end    = models.DateField ()
  code        = models.CharField (max_length = 5)
  start       = models.TimeField ()
  end         = models.TimeField ()
  lanch       = models.BooleanField      (default = True)
  break_day   = models.SmallIntegerField (default = 2)
  break_night = models.SmallIntegerField (default = 0)

class ShedulePlane (models.Model):
  '''Планируемый график'''
  class Meta ():
    db_table = 'shedule_plane'
  id    = models.AutoField (primary_key = True)
  data  = models.DateField (unique      = True)
  shift = models.CharField (max_length  = 5)

class SheduleReal (models.Model):
  '''Реальный график'''
  class Meta ():
    db_table = 'shedule_real'
  id          = models.AutoField         (primary_key = True      )
  data        = models.DateField         (unique      = True      )
  start       = models.TimeField         (                   null = True)
  end         = models.TimeField         (                   null = True)
  lanch       = models.BooleanField      (default = True)
  break_day   = models.SmallIntegerField (default = 2,       null = True)
  break_night = models.SmallIntegerField (default = 0,       null = True)
  delay       = models.TimeField         (default = '00:00', null = True)
  vacation    = models.BooleanField      (default = False)
  sick        = models.BooleanField      (default = False)

class Lanch (models.Model):
  '''Стоимость обеда'''
  class Meta ():
    db_table        = 'lanch'
    unique_together = (('start', 'end'))
  id    = models.AutoField    (primary_key = True)
  start = models.DateField    ()
  end   = models.DateField    (null        = True)
  value = models.DecimalField (max_digits  = 9, decimal_places = 4)

class Rate (models.Model):
  '''Почасовая ставка'''
  class Meta ():
    db_table        = 'rate'
    unique_together = (('start', 'end'))
  id    = models.AutoField    (primary_key = True)
  start = models.DateField    ()
  end   = models.DateField    (null        = True)
  value = models.DecimalField (max_digits  = 9, decimal_places = 4)

class CodesPayslip (models.Model):
  '''Коды НДФЛ'''
  INCOME      = 'i'
  CONSUMPTION = 'c'
  OTHERS      = 'o'
  TYPES = (
    (INCOME,      'Income'),
    (CONSUMPTION, 'Consumption'),
    (OTHERS,      'Others')
  )
  class Meta ():
    db_table = 'codes_payslip'
  id   = models.AutoField (primary_key = True)
  code = models.CharField (max_length  = 8, unique = True)
  name = models.CharField (max_length  = 50)
  type = models.CharField (max_length  = 1, choices = TYPES)

class Payslip (models.Model):
  '''Расчетный листок'''
  class Meta ():
    db_table = 'payslip'
  id          = models.AutoField    (primary_key = True)
  period      = models.DateField    (unique      = True)
  division    = models.CharField    (default = 'Оптовая продажа алкоголя', max_length  = 50)
  post        = models.CharField    (default = 'Кассир-оператор ПК',       max_length  = 50)
  rate        = models.DecimalField (             max_digits = 9, decimal_places = 2)
  begin_dolg  = models.DecimalField (default = 0, max_digits = 9, decimal_places = 2)
  rotate_dolg = models.DecimalField (null = True, max_digits = 9, decimal_places = 2)
  end_dolg    = models.DecimalField (default = 0, max_digits = 9, decimal_places = 2)
  income      = models.DecimalField (             max_digits = 9, decimal_places = 2)
  consumption = models.DecimalField (             max_digits = 9, decimal_places = 2)
  paying      = models.DecimalField (             max_digits = 9, decimal_places = 2)
  
