from django           import forms
from .models          import Azs, FuelTypes, PayTypes, Refuels
from calend_app.lists import DAYS, MONTHS, HOURS, MINUTES, SECONDS

class AddRefuelForm (forms.ModelForm):
  '''Добавление чека с заправки'''
  class Meta (object):
    model  = Refuels
    fields = [
      'azs',
      'check_number',
      'dispencer',
      'fuel_type',
      'fuel_count',
      'price',
      'cost',
      'pay_type',
      'score_plus',
      'score_minus',
      'score_total',
      'odometer',
      'consumption',
      'speed'
    ]

  day          = forms.ChoiceField      (label = 'День',    choices = DAYS)
  month        = forms.ChoiceField      (label = 'Месяц',   choices = MONTHS)
  hour         = forms.ChoiceField      (label = 'Часы',    choices = HOURS)
  minute       = forms.ChoiceField      (label = 'Минуты',  choices = MINUTES)
  second       = forms.ChoiceField      (label = 'Секунды', choices = SECONDS)

  azs          = forms.ModelChoiceField (label = 'АЗС',         queryset = Azs.objects.all       ())
  fuel_type    = forms.ModelChoiceField (label = 'Тип топлива', queryset = FuelTypes.objects.all ())
  pay_type     = forms.ModelChoiceField (label = 'Тип оплаты',  queryset = PayTypes.objects.all  ())

  check_number = forms.CharField        (label = 'Номер чека', max_length = 5, required  = False)

  dispencer    = forms.IntegerField     (label = 'Номер колонки',    min_value =    0, max_value =  10, initial =    5)
  year         = forms.IntegerField     (label = 'Год',              min_value = 1966,                  initial = 2017)
  speed        = forms.IntegerField     (label = 'Средняя скорость', min_value =    0, max_value = 150, initial =    0)

  fuel_count   = forms.DecimalField     (label = 'Количество топлива', min_value  = 0, max_value = 100,  max_digits = 6, decimal_places = 3, initial =     0)
  price        = forms.DecimalField     (label = 'Стоимость',          min_value  = 0,                   max_digits = 5, decimal_places = 2, initial = 39.79)
  cost         = forms.DecimalField     (label = 'Цена',               min_value  = 0,                   max_digits = 7, decimal_places = 2, initial =     0)
  score_plus   = forms.DecimalField     (label = 'Начислено баллов',   min_value  = 0,                   max_digits = 7, decimal_places = 2, initial =     0)
  score_minus  = forms.DecimalField     (label = 'Списано баллов',     min_value  = 0,                   max_digits = 7, decimal_places = 2, initial =     0)
  score_total  = forms.DecimalField     (label = 'Итого баллов',       min_value  = 0,                   max_digits = 7, decimal_places = 2, initial =     0)
  odometer     = forms.DecimalField     (label = 'Заправочный пробег', min_value  = 0, max_value = 1000, max_digits = 5, decimal_places = 1, initial =     0)
  consumption  = forms.DecimalField     (label = 'Расход топлива',     min_value  = 0,                   max_digits = 4, decimal_places = 1, initial =    10)

