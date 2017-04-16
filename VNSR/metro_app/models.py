from datetime             import datetime, date, time
from django.db            import models
from .functions           import decimal_to_money, control_period, time_to_int, int_to_time
from calend_app.functions import get_month_text, get_now
from calend_app.models    import Signs

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

  def __str__ (self):
    return '%s' % self.data

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
  rate        = models.DecimalField (             max_digits = 9, decimal_places = 4)
  begin_dolg  = models.DecimalField (default = 0, max_digits = 9, decimal_places = 4)
  rotate_dolg = models.DecimalField (null = True, max_digits = 9, decimal_places = 4)
  end_dolg    = models.DecimalField (default = 0, max_digits = 9, decimal_places = 4)
  income      = models.DecimalField (             max_digits = 9, decimal_places = 4)
  consumption = models.DecimalField (             max_digits = 9, decimal_places = 4)
  paying      = models.DecimalField (             max_digits = 9, decimal_places = 4)

  def print_edit (self):
    '''Приводит данные из БД к печатному виду'''
    self.rate        = decimal_to_money (self.rate)
    self.begin_dolg  = decimal_to_money (self.begin_dolg)
    self.rotate_dolg = decimal_to_money (self.rotate_dolg)
    self.end_dolg    = decimal_to_money (self.end_dolg)
    self.income      = decimal_to_money (self.income)
    self.consumption = decimal_to_money (self.consumption)
    self.paying      = decimal_to_money (self.paying)
    self.text_period = get_month_text (self.period.year, self.period.month)
    self.period      = str (self.period.month) + '/' + str (self.period.year) [2:4]
    if len (self.period) == 4: self.period = '0' + self.period

class PayslipDetails (models.Model):
  '''Детализация расчетного листка'''
  class Meta ():
    db_table        = 'payslip_details'
    unique_together = (('payslip', 'code', 'period'))

  id      = models.AutoField    (primary_key = True)
  payslip = models.ForeignKey   ('Payslip',      null = True, on_delete = models.SET_NULL, db_column = 'payslip')
  code    = models.ForeignKey   ('CodesPayslip', null = True, on_delete = models.SET_NULL, db_column = 'code')
  period  = models.DateField    ()
  summa   = models.DecimalField (null = True, max_digits = 9, decimal_places = 4)
  count   = models.DecimalField (null = True, max_digits = 9, decimal_places = 4)

  def print_edit (self):
    '''Приводит данные из БД к печатному виду'''
    self.type       = self.code.type
    self.code_print = self.code.code + ' ' + self.code.name
    self.period     = str (self.period.month) + '/' + str (self.period.year) [2:4]
    if len (self.period) == 4: self.period = '0' + self.period
    self.summa      = decimal_to_money (self.summa)
    self.count      = decimal_to_money (self.count)

