from django.db       import models
from menu_app.models import Users

# Create your models here.

class Links (models.Model):
  '''Ссылки'''
  class Meta ():
    db_table = 'links'

  id      = models.AutoField (primary_key = True)
  address = models.CharField (max_length  = 50)
  text    = models.CharField (max_length  = 50)

class UserLinks (models.Model):
  '''Сопоставление ссылок и пользователей'''
  class Meta ():
    db_table = 'user_links'

  id = models.AutoField (primary_key = True)
  user = models.ForeignKey (Users, db_column = 'user', on_delete = models.SET_DEFAULT, default = 0)
  link = models.ForeignKey (Links, db_column = 'link', on_delete = models.SET_DEFAULT, default = 0)

