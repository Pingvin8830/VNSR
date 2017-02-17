from django           import forms
from .models          import Cards, Debets, DebetTypes, Orgs, OrgTypes, Credits, Products, Cheques, CredCats
from calend_app.lists import MONTHS, DAYS, HOURS, MINUTES, SECONDS

TEMPLATES = (
  ( 'all',        'Подробно'),
  ( 'org', 'По организациям'),
  ('card',       'По счетам'),
  ('type',        'По типам'),
)

DIMENSIONS = (
  ('шт.', 'штуки'),
  ('кг.', 'килограммы'),
  ('л.',  'литры'),
)

# Create your forms here.

class AddCardForm (forms.ModelForm):
  '''Счета хранения средств'''
  class Meta ():
    model  = Cards
    fields = ['number', 'name', 'comment']

  number  = forms.CharField (label = 'Номер карточки', max_length = 20, required = False)
  name    = forms.CharField (label = 'Название',       max_length = 20)
  comment = forms.CharField (label = 'Комментарий',    max_length = 100, required = False)

class AddChequeForm (forms.ModelForm):
  '''Добавление чека'''
  class Meta ():
    model = Cheques
    fields = ['number', 'kassa', 'discount', 'org', 'card']

  year     = forms.IntegerField     (label = 'Год',         max_value  = 9999, initial = 2017)
  month    = forms.ChoiceField      (label = 'Месяц',       choices    = MONTHS)
  day      = forms.ChoiceField      (label = 'День',        choices    = DAYS)
  hour     = forms.ChoiceField      (label = 'Часы',        choices    = HOURS,   required   = False)
  minute   = forms.ChoiceField      (label = 'Минуты',      choices    = MINUTES, required   = False)
  second   = forms.ChoiceField      (label = 'Секунды',     choices    = SECONDS, required   = False)
  number   = forms.CharField        (label = 'Номер',       max_length = 5,       required   = False)
  kassa    = forms.IntegerField     (label = 'Касса',       min_value  = 0,       required   = False)
  discount = forms.DecimalField     (label = 'Скидка',      min_value  = 0,       max_digits = 9, decimal_places = 2, required = False)
  org      = forms.ModelChoiceField (label = 'Организация', queryset   = Orgs.objects.all ())
  card     = forms.ModelChoiceField (label = 'Счет',        queryset   = Cards.objects.all ())

class AddCreditForm (forms.ModelForm):
  '''Добавление расхода'''
  class Meta ():
    model = Credits
    fields = ['product', 'price', 'count', 'cost', 'is_need']

  price    = forms.DecimalField     (label = 'Цена',        min_value  = 0, max_digits = 9,  decimal_places = 2)
  count    = forms.DecimalField     (label = 'Количество',  min_value  = 0, max_digits = 9,  decimal_places = 2)
  cost     = forms.DecimalField     (label = 'Стоимость',   min_value  = 0, max_digits = 9,  decimal_places = 2, required = False)
  is_need  = forms.BooleanField     (label = 'Обязательно', initial    = True)
  product  = forms.ModelChoiceField (label = 'Товар',       queryset   = Products.objects.all ())

class AddCredCatForm (forms.ModelForm):
  '''Добавляет категорию расходов'''
  class Meta ():
    model  = CredCats
    fields = ['name', 'comment']

  name    = forms.CharField (label = 'Название',    max_length = 50)
  comment = forms.CharField (label = 'Комментарий', max_length = 100, required = False)

class AddDebetForm (forms.ModelForm):
  '''Доходы'''
  class Meta ():
    model = Debets
    fields = ['comment', 'summa', 'type', 'card', 'payer']

  year    = forms.IntegerField     (label = 'Год',         max_value  = 9999, initial = 2017)
  month   = forms.ChoiceField      (label = 'Месяц',       choices    = MONTHS)
  day     = forms.ChoiceField      (label = 'День',        choices    = DAYS)
  hour    = forms.ChoiceField      (label = 'Часы',        choices    = HOURS,   required   = False)
  minute  = forms.ChoiceField      (label = 'Минуты',      choices    = MINUTES, required   = False)
  second  = forms.ChoiceField      (label = 'Секунды',     choices    = SECONDS, required   = False)
  comment = forms.CharField        (label = 'Комментарий', max_length = 100,     required   = False)
  summa   = forms.DecimalField     (label = 'Сумма',       min_value  = 0,       max_digits = 9, decimal_places = 2)
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

class AddProductForm (forms.ModelForm):
  '''Добавляет товар'''
  class Meta ():
    model  = Products
    fields = ['name', 'dimension', 'comment', 'category']

  name      = forms.CharField        (label = 'Название',    max_length = 50)
  dimension = forms.ChoiceField      (label = 'Ед.',         choices    = DIMENSIONS)
  comment   = forms.CharField        (label = 'Комментарий', max_length = 100, required = False)
  category  = forms.ModelChoiceField (label = 'Категория',   queryset   = CredCats.objects.all ())

class CaseChequeForm (forms.Form):
  '''Выбор чека'''

  cheque = forms.ModelChoiceField (label = 'Чек', queryset = Cheques.objects.all ())

class CasePeriodForm (forms.Form):
  '''Выбор периода'''

  year_start  = forms.IntegerField (label = 'Год',           min_value = 0,      max_value = 9999, initial = 2017)
  year_end    = forms.IntegerField (label = 'Год',           min_value = 0,      max_value = 9999, initial = 2017)
  month_start = forms.ChoiceField  (label = 'Месяц',         choices   = MONTHS, initial =  1)
  month_end   = forms.ChoiceField  (label = 'Месяц',         choices   = MONTHS, initial = 12)
  day_start   = forms.ChoiceField  (label = 'День',          choices   = DAYS,   initial =  1)
  day_end     = forms.ChoiceField  (label = 'День',          choices   = DAYS,   initial = 31)
  template    = forms.ChoiceField  (label = 'Представление', choices   = TEMPLATES)

