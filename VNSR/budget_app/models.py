from django.db import models

# Create your models here.

class Cards (models.Model):
  '''Банковские карты'''
  class Meta (object):
    db_table = 'cards'

  id      = models.AutoField (primary_key = True)
  number  = models.CharField (max_length  = 20, unique = True)
  name    = models.CharField (max_length  = 20)
  comment = models.CharField (max_length  = 50)

class Debets (models.Model):
  '''Поступления средств'''
  class Meta (object):
    db_table = 'debets'

  id    = models.AutoField    (primary_key = True)
  date  = models.DateField    ()
  type  = models.ForeignKey   ('DebetTypes', null = True, on_delete = models.SET_NULL, db_column = 'type')
  card  = models.ForeignKey   ('Cards',      null = True, on_delete = models.SET_NULL, db_column = 'card')
  summa = models.DecimalField (max_digits = 9, decimal_places = 4)
  payer = models.CharField    (max_length = 50)

class DebetTypes (models.Model):
  '''Типы поступлений средств'''
  class Meta (object):
    db_table = 'debet_types'

  id      = models.AutoField (primary_key = True)
  name    = models.CharField (max_length = 50, unique = True)
  comment = models.CharField (max_length = 50)

class Organizations (models.Model):
  '''Организации'''
  class Meta (object):
    db_table = 'organizations'

  id      = models.AutoField (primary_key = True)
  name    = models.CharField (max_length = 50)
  address = models.CharField (max_length = 100)
  type    = models.CharField (max_length = 30)

