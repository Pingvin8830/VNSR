from django.db import models

# Create your models here.

class Debets (models.Model):
  '''Поступление средств'''
  class Meta (object):
    db_table = 'debets'

  id      = models.AutoField    (primary_key = True)
  date    = models.DateField    (null = False)
  time    = models.TimeField    (null = True)
  summa   = models.DecimalField (null = False, max_digits = 11, decimal_places = 4)
  comment = models.CharField    (null = True,  max_length = 100)
  type    = models.ForeignKey   ('DebetTypes', db_column = 'type',  null = False, on_delete = models.SET_DEFAULT, default = -1)
  card    = models.ForeignKey   ('Cards',      db_column = 'card',  null = False, on_delete = models.SET_DEFAULT, default = -1)
  payer   = models.ForeignKey   ('Orgs',       db_column = 'payer', null = False, on_delete = models.SET_DEFAULT, default = -1)

class DebetTypes (models.Model):
  '''Типы поступлений'''
  class Meta (object):
    db_table = 'debet_types'

  def __str__ (self):
    return '%s' % self.name

  id      = models.AutoField (primary_key = True)
  name    = models.CharField (null = False, max_length = 50)
  comment = models.CharField (null = True,  max_length = 100)

class Cards (models.Model):
  '''Счета хранения средств'''
  class Meta (object):
    db_table = 'cards'

  def __str__ (self):
    return '%s' % self.name

  id      = models.AutoField (primary_key = True)
  number  = models.CharField (null = True,  max_length = 50)
  name    = models.CharField (null = False, max_length = 50)
  comment = models.CharField (null = True,  max_length = 100)

class Orgs (models.Model):
  '''Организации и частные лица'''
  class Meta (object):
    db_table = 'organizations'

  def __str__ (self):
    return '%s' % self.name

  id      = models.AutoField            (primary_key = True)
  name    = models.CharField            (null = False, max_length = 100)
  region  = models.CharField            (null = True,  max_length = 100, default = 'СПб')
  city    = models.CharField            (null = True,  max_length = 50)
  street  = models.CharField            (null = True,  max_length = 50)
  house   = models.PositiveIntegerField (null = True)
  build   = models.CharField            (null = True,  max_length = 2)
  flat    = models.PositiveIntegerField (null = True)
  phone   = models.CharField            (null = True,  max_length = 50)
  comment = models.CharField            (null = True,  max_length = 100)
  type    = models.ForeignKey           ('OrgTypes',   db_column = 'type', null = False, on_delete = models.SET_DEFAULT, default = -1)

class OrgTypes (models.Model):
  '''Типы организаций'''
  class Meta (object):
    db_table = 'organization_types'

  def __str__ (self):
    return '%s' % self.name

  id      = models.AutoField (primary_key = True)
  name    = models.CharField (null = False, max_length = 50)
  comment = models.CharField (null = True,  max_length = 100)

class Credits (models.Model):
  '''Расходы'''
  class Meta (object):
    db_table = 'credits'

  id      = models.AutoField    (primary_key = True) 
  price   = models.DecimalField (null = False, max_digits = 9, decimal_places = 4)
  count   = models.DecimalField (null = False, max_digits = 9, decimal_places = 4)
  cost    = models.DecimalField (null = True,  max_digits = 9, decimal_places = 4, default = 0)
  is_need = models.BooleanField (default = True)
  product = models.ForeignKey   ('Products',   db_column = 'product', null = False, on_delete = models.SET_DEFAULT, default = -1)
  cheque  = models.ForeignKey   ('Cheques',    db_column = 'cheque',  null = False, on_delete = models.SET_DEFAULT, default = -1)

class Products (models.Model):
  '''Товары'''
  class Meta (object):
    db_table = 'products'

  DIMENSIONS = (('шт.', 'штуки'), ('кг.', 'килограммы'))

  id        = models.AutoField  (primary_key = True)
  name      = models.CharField  (null = False, max_length = 50)
  dimension = models.CharField  (null = False, max_length = 20, choices = DIMENSIONS)
  comment   = models.CharField  (null = True,  max_length = 100)
  category  = models.ForeignKey ('CredCats',   db_column = 'category', null = False, on_delete = models.SET_DEFAULT, default = -1)

class CredCats (models.Model):
  '''Категории трат'''
  class Meta (object):
    db_table = 'credit_categories'

  id      = models.AutoField (primary_key = True)
  name    = models.CharField (null = False, max_length = 50)
  comment = models.CharField (null = True,  max_length = 100)

class Cheques (models.Model):
  '''Чеки'''
  class Meta (object):
    db_table = 'cheques'

  def __str__ (self):
    return '%s - %s' % (self.number, self.org.name)

  id       = models.AutoField    (primary_key = True)
  number   = models.CharField    (null = True, max_length = 5)
  date     = models.DateField    (null = False)
  time     = models.TimeField    (null = True)
  kassa    = models.IntegerField (null = True)
  discount = models.DecimalField (null = False, default = 0, max_digits = 9, decimal_places = 2)
  org      = models.ForeignKey   ('Orgs',  db_column = 'org',  null = False, on_delete = models.SET_DEFAULT, default = -1)
  card     = models.ForeignKey   ('Cards', db_column = 'card', null = False, on_delete = models.SET_DEFAULT, default = -1)

