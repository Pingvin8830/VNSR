from django.db import models

# Create your models here.
class Humans (models.Model):
  '''Люди'''

  class Meta (object):
    db_table = 'humans'

  id          = models.AutoField (primary_key = True)
  family      = models.CharField (max_length = 20)
  name        = modles.CharField (max_length = 20)
  father_name = models.CharField (max_length = 20)
  birthday    = models.DateField ()
  deadday     = models.DateField ()
  comment     = models.CharField (max_length = 250)

class Relations (models.Model):
  '''Варианты связей'''

  class Meta (object):
    db_table = 'relations'

  id   = models.AutoField (primary_key = True)
  name = models.CharField (max_length = 20)

class HumansRelations (models.Model):
  '''Связи людей'''

  class Meta (object):
    db_table = 'humans_relations'

  id        = models.AutoField  (primary_key = True)
  human_1   = models.ForeignKey (Humans,    on_delete = models.SET_NULL, null = True, db_column = 'human_1', related_name = 'human_1')
  human_2   = models.ForeignKey (Humans,    on_delete = models.SET_NULL, null = True, db_column = 'human_2', related_name = 'human_2')
  relation  = models.ForeignKey (Relations, on_delete = models.SET_NULL, null = True, db_column = 'relation')

class EventTypes (models.Models):
  '''Типы событий'''

  class Meta (object):
    db_table = 'event_types'

  id   = models.AutoField (primary_key = True)
  name = models.CharField (max_length = 20)

class Events (models.Model):
  '''События'''

  class Meta (object):
    db_table = 'events'

  id          = models.AutoField  (primary_key = True)
  date        = models.DateField  ()
  time        = models.TimeField  ()
  description = models.CharField  (max_length = 250)
  human       = models.ForeignKey (Humans,     on_delete = models.SET_NULL, null = True, db_column = 'human')
  type        = models.ForeignKey (EventTypes, on_delete = models.SET_NULL, null = True, db_column = 'type')

