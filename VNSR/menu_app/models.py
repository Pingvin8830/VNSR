from django.db import models
from django.contrib.auth.models import User

# Create your models here.
class Items (models.Model):
	'''
		Пункты меню
	'''
	class Meta ():
		db_table = 'items'
	id   = models.IntegerField (primary_key = True)
	href = models.CharField    (max_length  = 100, verbose_name = 'Ссылка')
	text = models.CharField    (max_length  = 100, verbose_name = 'Подпись')

class ItemsUsers (models.Model):
	'''
		Сопоставление пунктов меню с пользователями
	'''
	class Meta ():
		db_table = 'items_users'
	id   = models.IntegerField (primary_key = True)
	item = models.ForeignKey   (Items)
	user = models.CharField    (max_length = 30)
