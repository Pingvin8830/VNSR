from django.db import models

# Create your models here.

class ItemsMenu (models.Model):
  '''Пункты меню для пользователей - приложения'''
  class Meta ():
    db_table            = 'items_menu'
    verbose_name        = 'Приложение'
    verbose_name_plural = 'Приложения'

  id      = models.AutoField (primary_key = True)
  app     = models.CharField (max_length  = 100,              verbose_name = 'Ссылка')
  text    = models.CharField (max_length  = 100,              verbose_name = 'Подпись')
  comment = models.CharField (max_length  = 100, null = True, verbose_name = 'Комментарий')

  def __str__ (self):
    return self.text

class Users (models.Model):
  '''Пользователи'''
  class Meta ():
    db_table            = 'users'
    verbose_name        = 'Пользователь'
    verbose_name_plural = 'Пользователи'

  id   = models.AutoField (primary_key = True)
  name = models.CharField (max_length  = 10,   verbose_name = 'Логин')

  def __str__ (self):
    return self.name

class UserMenu (models.Model):
  '''Сопоставление пользователям пунктов меню - приложений'''
  class Meta ():
    db_table            = 'user_menu'
    verbose_name        = 'Меню пользователя'
    verbose_name_plural = 'Меню пользователей'

  id   = models.AutoField  (primary_key = True)
  user = models.ForeignKey (Users, verbose_name     = 'Пользователь')
  item = models.ForeignKey (ItemsMenu, verbose_name = 'Приложение')

  def __str__ (self):
    return self.user.name + ' / ' + self.item.text

class ItemsApp (models.Model):
  '''Пункты меню для приложений - действия'''
  class Meta ():
    db_table            = 'items_app'
    verbose_name        = 'Подпункт меню'
    verbose_name_plural = 'Подпункты меню'

  id      = models.AutoField (primary_key = True)
  text    = models.CharField (max_length  =  20,              verbose_name = 'Название подпункта')
  href    = models.CharField (max_length  =  20,              verbose_name = 'Вторая часть url')
  comment = models.CharField (max_length  = 100, null = True, verbose_name = 'Комментарий')

  def __str__ (self):
    return self.text

class AppMenu (models.Model):
  '''Сопоставление приложениям пунктов меню - действий'''
  class Meta ():
    db_table            = 'app_menu'
    verbose_name        = 'Меню приложения'
    verbose_name_plural = 'Меню приложений'

  id   = models.AutoField  (primary_key = True)
  app  = models.ForeignKey (ItemsMenu, verbose_name = 'Приложение')
  item = models.ForeignKey (ItemsApp,  verbose_name = 'Ссылка')

  def __str__ (self):
    return self.app.text + ' / ' + self.item.text

