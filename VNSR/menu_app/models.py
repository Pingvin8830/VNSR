from django.db import models

# Create your models here.
class ItemsMenu (models.Model):
	'''
		Пункты меню для пользователей - приложения
	'''
	class Meta ():
		db_table = 'items_menu'
	id   = models.IntegerField (primary_key = True)
	href = models.CharField    (max_length  = 100, verbose_name = 'Ссылка')
	text = models.CharField    (max_length  = 100, verbose_name = 'Подпись')

class Users (models.Model):
	'''
		Пользователи
	'''
	class Meta ():
		db_table = 'users'
	id   = models.IntegerField (primary_key = True)
	name = models.CharField    (max_length  = 10,   verbose_name = 'Логин')

class UserMenu (models.Model):
	'''
		Сопоставление пользователям пунктов меню - приложений
	'''
	class Meta ():
		db_table = 'user_menu'
	id   = models.IntegerField (primary_key = True)
	user = models.ForeignKey   (Users)
	item = models.ForeignKey   (ItemsMenu)

class ItemsApp (models.Model):
	'''
		Пункты меню для приложений - действия
	'''
	class Meta ():
		db_table = 'items_app'
	id     = models.IntegerField (primary_key = True)
	text   = models.CharField    (max_length = 20, verbose_name = 'Название подпункта')
	href   = models.CharField    (max_length = 20, verbose_name = 'Вторая часть url')

class AppMenu (models.Model):
	'''
		Сопоставление приложениям пунктов меню - действий
	'''
	class Meta ():
		db_table = 'app_menu'
	id   = models.IntegerField (primary_key = True)
	app  = models.ForeignKey   (ItemsMenu)
	item = models.ForeignKey   (ItemsApp)

