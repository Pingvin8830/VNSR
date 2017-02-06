from django  import forms
from .models import Cards, Debets, DebetTypes, Orgs, OrgTypes

MONTHS = (
  ( 1, 'Январь'),
  ( 2, 'Февраль'),
  ( 3, 'Март'),
  ( 4, 'Апрель'),
  ( 5, 'Май'),
  ( 6, 'Июнь'),
  ( 7, 'Июль'),
  ( 8, 'Август'),
  ( 9, 'Сентябрь'),
  (10, 'Октябрь'),
  (11, 'Ноябрь'),
  (12, 'Декабрь'),
)

DAYS = (
  ( 1,  '1'),
  ( 2,  '2'),
  ( 3,  '3'),
  ( 4,  '4'),
  ( 5,  '5'),
  ( 6,  '6'),
  ( 7,  '7'),
  ( 8,  '8'),
  ( 9,  '9'),
  (10, '10'),
  (11, '11'),
  (12, '12'),
  (13, '13'),
  (14, '14'),
  (15, '15'),
  (16, '16'),
  (17, '17'),
  (18, '18'),
  (19, '19'),
  (20, '20'),
  (21, '21'),
  (22, '22'),
  (23, '23'),
  (24, '24'),
  (25, '25'),
  (26, '26'),
  (27, '27'),
  (28, '28'),
  (29, '29'),
  (30, '30'),
  (31, '31'),
)

HOURS = (
  ( 0,  '0'),
  ( 1,  '1'),
  ( 2,  '2'),
  ( 3,  '3'),
  ( 4,  '4'),
  ( 5,  '5'),
  ( 6,  '6'),
  ( 7,  '7'),
  ( 8,  '8'),
  ( 9,  '9'),
  (10, '10'),
  (11, '11'),
  (12, '12'),
  (13, '13'),
  (14, '14'),
  (15, '15'),
  (16, '16'),
  (17, '17'),
  (18, '18'),
  (19, '19'),
  (20, '20'),
  (21, '21'),
  (22, '22'),
  (23, '23'),
)

MINUTES = (
  ( 0,  '0'),
  ( 1,  '1'),
  ( 2,  '2'),
  ( 3,  '3'),
  ( 4,  '4'),
  ( 5,  '5'),
  ( 6,  '6'),
  ( 7,  '7'),
  ( 8,  '8'),
  ( 9,  '9'),
  (10, '10'),
  (11, '11'),
  (12, '12'),
  (13, '13'),
  (14, '14'),
  (15, '15'),
  (16, '16'),
  (17, '17'),
  (18, '18'),
  (19, '19'),
  (20, '20'),
  (21, '21'),
  (22, '22'),
  (23, '23'),
  (24, '24'),
  (25, '25'),
  (26, '26'),
  (27, '27'),
  (28, '28'),
  (29, '29'),
  (30, '30'),
  (31, '31'),
  (32, '32'),
  (33, '33'),
  (34, '34'),
  (35, '35'),
  (36, '36'),
  (37, '37'),
  (38, '38'),
  (39, '39'),
  (40, '40'),
  (41, '41'),
  (42, '42'),
  (43, '43'),
  (44, '44'),
  (45, '45'),
  (46, '46'),
  (47, '47'),
  (48, '48'),
  (49, '49'),
  (50, '50'),
  (51, '51'),
  (52, '52'),
  (53, '53'),
  (54, '54'),
  (55, '55'),
  (56, '56'),
  (57, '57'),
  (58, '58'),
  (59, '59'),
)

SECONDS = MINUTES

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
    model = Debets
    fields = ['comment', 'summa', 'type', 'card', 'payer']

  year    = forms.IntegerField     (label = 'Год',         max_value  = 9999)
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

class CasePeriodForm (forms.Form):
  '''Выбор периода'''

  year_start  = forms.IntegerField (label = 'Год',   min_value = 0,  max_value = 9999, initial = 2017)
  year_end    = forms.IntegerField (label = 'Год',   min_value = 0,  max_value = 9999, initial = 2017)
  month_start = forms.ChoiceField  (label = 'Месяц', choices   = MONTHS)
  month_end   = forms.ChoiceField  (label = 'Месяц', choices   = MONTHS)
  day_start   = forms.ChoiceField  (label = 'День',  choices   = DAYS)
  day_end     = forms.ChoiceField  (label = 'День',  choices   = DAYS)

