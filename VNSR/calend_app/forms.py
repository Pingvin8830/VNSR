from django import forms

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

