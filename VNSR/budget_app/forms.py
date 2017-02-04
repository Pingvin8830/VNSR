from django  import forms
from .models import Cards, Debets, DebetTypes, Orgs, OrgTypes

# Create your forms here.

class AddCardForm (forms.ModelForm):
  '''Счета хранения средств'''
  class Meta ():
    model  = Cards
    fields = ['number', 'name', 'comment']

  number  = forms.CharField (label = 'Номер карточки', max_length = 20, required = False)
  name    = forms.CharField (label = 'Название',       max_length = 20)
  comment = forms.CharField (label = 'Комментарий',    max_length = 100, required = False)

class AddDebetForm (forms.ModelForm):
  '''Доходы'''
  class Meta ():
    model  = Debets
    fields = ['date', 'time', 'summa', 'comment', 'type', 'card', 'payer']

  date    = forms.DateField        (label = 'Дата')
  time    = forms.TimeField        (label = 'Время',                         required   = False)
  comment = forms.CharField        (label = 'Комментарий', max_length = 100, required   = False)
  summa   = forms.DecimalField     (label = 'Сумма',       min_value  = 0,   max_digits = 9, decimal_places = 2)
  type    = forms.ModelChoiceField (label = 'Тип',         queryset   = DebetTypes.objects.all ())
  card    = forms.ModelChoiceField (label = 'Счет',        queryset   = Cards.objects.all ())
  payer   = forms.ModelChoiceField (label = 'Источник',    queryset   = Orgs.objects.all ())

class AddDebetTypeForm (forms.ModelForm):
  '''Типы дохода'''
  class Meta ():
    model  = DebetTypes
    fields = ['name', 'comment']

  name    = forms.CharField (label = 'Название',    max_length = 50)
  comment = forms.CharField (label = 'Комментарий', max_length = 100, required = False)

class AddOrgForm (forms.ModelForm):
  '''Организации'''
  class Meta ():
    model = Orgs
    fields = ['name', 'city', 'street', 'house', 'build', 'flat', 'phone', 'type']

  name   = forms.CharField        (label = 'Название / ФИО',  max_length = 100)
  city   = forms.CharField        (label = 'Город',           max_length = 50, required = False)
  street = forms.CharField        (label = 'Улица',           max_length = 50, required = False)
  house  = forms.IntegerField     (label = 'Дом',             min_value  = 0,  required = False)
  build  = forms.CharField        (label = 'Корпус / Литера', max_length = 2,  required = False)
  flat   = forms.IntegerField     (label = 'Квартира',        min_value  = 0,  required = False)
  phone  = forms.CharField        (label = 'Телефон',         max_length = 20, required = False)
  type   = forms.ModelChoiceField (label = 'Тип', queryset = OrgTypes.objects.all ())

class AddOrgTypeForm (forms.ModelForm):
  '''Типы организаций'''
  class Meta ():
    model  = OrgTypes
    fields = ['name', 'comment']

  name    = forms.CharField (label = 'Название',    max_length = 50)
  comment = forms.CharField (label = 'Комментарий', max_length = 100, required = False)

