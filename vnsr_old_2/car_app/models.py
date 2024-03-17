from django.db import models

# Create your models here.

class Azs (models.Model):
  '''Авто-заправочные станции'''
  class Meta (object):
    db_table = 'azs'

  id      = models.AutoField (primary_key = True)
  company = models.CharField (max_length = 100)
  name    = models.CharField (max_length = 20)
  address = models.CharField (max_length = 100)
  phone   = models.CharField (max_length = 15)
  comment = models.CharField (max_length = 100)

  def __str__ (self):
    return '%s - %s' % (self.company, self.name)

class FuelTypes (models.Model):
  '''Типы топлива'''
  class Meta (object):
    db_table = 'fuel_types'

  id      = models.AutoField (primary_key = True)
  name    = models.CharField (max_length = 30, null = False)
  comment = models.CharField (max_length = 100)

  def __str__ (self):
    return self.name

class PayTypes (models.Model):
  '''Типы оплаты'''
  class Meta (object):
    db_table = 'pay_types'

  id       = models.AutoField (primary_key = True)
  name     = models.CharField (max_length = 50, null = False)
  card_num = models.CharField (max_length = 30)
  comment  = models.CharField (max_length = 100)

  def __str__ (self):
    return self.name

class Refuels (models.Model):
  '''Заправки автомобиля'''
  class Meta (object):
    db_table = 'refuels'

  id           = models.AutoField                 (primary_key = True)
  azs          = models.ForeignKey                ('Azs',       on_delete = models.SET (1), null = False, default = 1, db_column = 'azs')
  check_number = models.CharField                 (max_length = 5)
  dispencer    = models.SmallIntegerField         ()
  fuel_type    = models.ForeignKey                ('FuelTypes', on_delete = models.SET (1), null = False, db_column = 'fuel_type')
  fuel_count   = models.DecimalField              (max_digits = 6, decimal_places = 3, null = False)
  price        = models.DecimalField              (max_digits = 5, decimal_places = 2)
  cost         = models.DecimalField              (max_digits = 7, decimal_places = 2)
  pay_type     = models.ForeignKey                ('PayTypes', on_delete = models.SET_NULL, null = True, db_column = 'pay_type')
  score_plus   = models.DecimalField              (max_digits = 7, decimal_places = 2, default = 0, null = False)
  score_minus  = models.DecimalField              (max_digits = 7, decimal_places = 2, default = 0, null = False)
  score_total  = models.DecimalField              (max_digits = 7, decimal_places = 2, default = 0, null = False)
  date_time    = models.DateTimeField             (null = False)
  odometer     = models.DecimalField              (max_digits = 5, decimal_places = 1, null = False)
  consumption  = models.DecimalField              (max_digits = 4, decimal_places = 1)
  speed        = models.PositiveSmallIntegerField ()

class Travels (models.Model):
  '''Поездки'''
  class Meta (object):
    db_table = 'travels'

  id              = models.AutoField            (primary_key = True)
  point_start     = models.CharField            (max_length = 100)
  point_end       = models.CharField            (max_length = 100)
  date_time_start = models.DateTimeField        ()
  date_time_end   = models.DateTimeField        ()
  distance        = models.PositiveIntegerField ()
  comment         = models.CharField            (max_length = 100, null = True)

class CheckPoints (models.Model):
  '''Контрольные точки поездки'''
  class Meta (object):
    db_table = 'check_points'

  id            = models.AutoField            (primary_key = True)
  travel        = models.ForeignKey           ('Travels', on_delete = models.SET (-1), null = False, db_column = 'travel')
  name          = models.CharField            (max_length = 100)
  date_time_in  = models.DateTimeField        (null = True)
  date_time_out = models.DateTimeField        (null = True)
  odometer      = models.PositiveIntegerField ()
  comment       = models.CharField            (max_length = 100, null = True)

