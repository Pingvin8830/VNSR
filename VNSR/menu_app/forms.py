from django.forms import ModelForm
from .models      import ItemsMenu, Users, ItemsApp

# Create your models here.
class ItemsMenuForm (ModelForm):
  '''Пункты меню для пользователей - приложения'''
  class Meta ():
    model = ItemsMenu
    fields = [
      'app',
      'text',
    ]

class UsersForm (ModelForm):
  '''Пользователи'''
  class Meta ():
    model = Users
    fields = [
      'name',
    ]

class ItemsAppForm (ModelForm):
  '''Пункты меню для приложений - действия'''
  class Meta ():
    model = ItemsApp
    fields = [
      'text',
      'href',
    ]

