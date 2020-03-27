from django import forms
from .lists import DAYS, MONTHS

class CalendLabels (forms.Form):
  '''Форма с метками дат и времени'''

  date   = forms.BooleanField (label = 'Дата')
  year   = forms.BooleanField (label = 'Год')
  month  = forms.BooleanField (label = 'Месяц')
  day    = forms.BooleanField (label = 'День')

  time   = forms.BooleanField (label = 'Время')
  hour   = forms.BooleanField (label = 'Час')
  minute = forms.BooleanField (label = 'Минута')
  second = forms.BooleanField (label = 'Секунда')

class CasePeriodForm (forms.Form):
  '''Выбор периода'''

  start       = forms.CharField    (label = 'Начало периода',    max_length = 0, required  = False)
  end         = forms.CharField    (label = 'Окончание периода', max_length = 0, required  = False)
  start_year  = forms.IntegerField (label = 'Год',               min_value  = 0, max_value = 9999, initial = 2017)
  end_year    = forms.IntegerField (label = 'Год',               min_value  = 0, max_value = 9999, initial = 2017)
  start_month = forms.ChoiceField  (label = 'Месяц',             choices    = MONTHS)
  end_month   = forms.ChoiceField  (label = 'Месяц',             choices    = MONTHS)
  start_day   = forms.ChoiceField  (label = 'День',              choices    = DAYS)
  end_day     = forms.ChoiceField  (label = 'День',              choices    = DAYS)
