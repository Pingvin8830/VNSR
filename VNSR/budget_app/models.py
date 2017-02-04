from django.db import models

# Create your models here.

class Debets (models.Model):
  '''Поступление средств'''
  class Meta (object):
    db_table = 'debets'

  id      = models.AutoField    (primary_key = True)
  date    = models.DateField    (null = False)
  time    = models.TimeField    (null = True)
  summa   = models.DecimalField (null = False, max_digits = 9, decimal_places = 4)
  comment = models.CharField    (null = True,  max_length = 100)
  type    = models.ForeignKey   ('DebetTypes', db_column = 'type',  null = False, on_delete = models.SET_DEFAULT, default = 0)
  card    = models.ForeignKey   ('Cards',      db_column = 'card',  null = False, on_delete = models.SET_DEFAULT, default = 0)
  payer   = models.ForeignKey   ('Orgs',       db_column = 'payer', null = False, on_delete = models.SET_DEFAULT, default = 0)

class DebetTypes (models.Model):
  '''Типы поступлений'''
  class Meta (object):
    db_table = 'debet_types'

  id      = models.AutoField (primary_key = True)
  name    = models.CharField (null = False, max_length = 50)
  comment = models.CharField (null = True,  max_length = 100)

class Cards (models.Model):
  '''Счета хранения средств'''
  class Meta (object):
    db_table = 'cards'

  id      = models.AutoField (primary_key = True)
  number  = models.CharField (null = True,  max_length = 50)
  name    = models.CharField (null = False, max_length = 50)
  comment = models.CharField (null = True,  max_length = 100)

class Orgs (models.Model):
  '''Организации и частные лица'''
  class Meta (object):
    db_table = 'organizations'

  id      = models.AutoField  (primary_key = True)
  name    = models.CharField  (null = False, max_length = 100)
  address = models.CharField  (null = True,  max_length = 100)
  phone   = models.CharField  (null = True,  max_length = 50)
  type    = models.ForeignKey ('OrgTypes',   db_column = 'type', null = False, on_delete = models.SET_DEFAULT, default = 0)

class OrgTypes (models.Model):
  '''Типы организаций'''
  class Meta (object):
    db_table = 'organization_types'

  id      = models.AutoField (primary_key = True)
  name    = models.CharField (null = False, max_length = 50)
  comment = models.CharField (null = True,  max_length = 100)

class Credits (models.Model):
  '''Расходы'''
  class Meta (object):
    db_table = 'credits'

  id      = models.AutoField    (primary_key = True) 
  date    = models.DateField    (null = False)
  time    = models.TimeField    (null = True)
  price   = models.DecimalField (null = False, max_digits = 9, decimal_places = 4)
  count   = models.DecimalField (null = False, max_digits = 9, decimal_places = 4)
  is_need = models.BooleanField (default = True)
  product = models.ForeignKey   ('Products',   db_column = 'product', null = False, on_delete = models.SET_DEFAULT, default = 0)

class Products (models.Model):
  '''Товары'''
  class Meta (object):
    db_table = 'products'

  DIMENSIONS = (('шт.', 'штуки'), ('кг.', 'килограммы'))

  id        = models.AutoField  (primary_key = True)
  name      = models.CharField  (null = False, max_length = 50)
  dimension = models.CharField  (null = False, max_length = 20, choices = DIMENSIONS)
  comment   = models.CharField  (null = True,  max_length = 100)
  category  = models.ForeignKey ('CredCats',   db_column = 'category', null = False, on_delete = models.SET_DEFAULT, default = 0)

class CredCats (models.Model):
  '''Категории трат'''
  class Meta (object):
    db_table = 'credit_categories'

  id      = models.AutoField (primary_key = True)
  name    = models.CharField (null = False, max_length = 50)
  comment = models.CharField (null = True,  max_length = 100)

