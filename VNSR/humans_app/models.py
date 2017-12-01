from django.db import models
from .lists    import SEXS

# Create your models here.
class Humans (models.Model):
  '''Люди'''

  class Meta (object):
    db_table = 'humans'

  id           = models.AutoField        (primary_key = True)
  sex          = models.NullBooleanField (choices = SEXS)
  family       = models.CharField        (max_length = 20)
  first_family = models.CharField        (max_length = 20, null = True)
  name         = models.CharField        (max_length = 20)
  father_name  = models.CharField        (max_length = 20, null = True)
  birthday     = models.DateField        (null = True)
  deadday      = models.DateField        (null = True)
  comment      = models.CharField        (max_length = 250, null = True)

  def __str__ (self):
    r = self.family
    if self.first_family: r += ' (' + self.first_family + ')'
    r += ' ' + self.name + ' ' + self.father_name
    return r

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

class EventTypes (models.Model):
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
  time        = models.TimeField  (null = True)
  description = models.CharField  (max_length = 250)
  human       = models.ForeignKey (Humans,     on_delete = models.SET_NULL, null = True, db_column = 'human')
  type        = models.ForeignKey (EventTypes, on_delete = models.SET_NULL, null = True, db_column = 'type')

