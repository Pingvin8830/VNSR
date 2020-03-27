from django           import forms
from .models          import Azs, CheckPoints, FuelTypes, PayTypes, Refuels, Travels
from calend_app.lists import DAYS, MONTHS, HOURS, MINUTES, SECONDS

class AddAzsForm (forms.ModelForm):
  '''Добавление автозаправочной станции'''
  class Meta (object):
    model  = Azs
    fields = [
      'company',
      'name',
      'address',
      'phone',
      'comment',
    ]

  company = forms.CharField (required = True,  label = 'Компания',    max_length = 100)
  name    = forms.CharField (required = True,  label = 'Название',    max_length = 20)
  address = forms.CharField (required = True,  label = 'Адрес',       max_length = 100)
  phone   = forms.CharField (required = False, label = 'Телефон',     max_length = 15)
  comment = forms.CharField (required = False, label = 'Комментарий', max_length = 100, widget = forms.Textarea)

class AddCheckPointForm (forms.ModelForm):
  '''Добавление контрольной точки'''
  class Meta (object):
    model  = CheckPoints
    fields = [
      'name',
      'odometer',
      'comment',
    ]

  inside     = forms.CharField    (required = False, label = 'Прибытие',         max_length = 0)
  outside    = forms.CharField    (required = False, label = 'Отъезд',           max_length = 0)
  name       = forms.CharField    (required = True,  label = 'Название',         max_length = 100)
  year_in    = forms.IntegerField (required = False, label = 'Год прибытия',     min_value  = 1966,)
  month_in   = forms.ChoiceField  (required = False, label = 'Месяц прибытия',   choices    = MONTHS)
  day_in     = forms.ChoiceField  (required = False, label = 'День прибытия',    choices    = DAYS)
  hour_in    = forms.ChoiceField  (required = False, label = 'Час прибытия',     choices    = HOURS)
  minute_in  = forms.ChoiceField  (required = False, label = 'Минута прибытия',  choices    = MINUTES)
  second_in  = forms.ChoiceField  (required = False, label = 'Секунда прибытия', choices    = SECONDS)
  year_out   = forms.IntegerField (required = False, label = 'Год отъезда',      min_value  = 1966)
  month_out  = forms.ChoiceField  (required = False, label = 'Месяц отъезда',    choices    = MONTHS)
  day_out    = forms.ChoiceField  (required = False, label = 'День отъезда',     choices    = DAYS)
  hour_out   = forms.ChoiceField  (required = False, label = 'Час отъезда',      choices    = HOURS)
  minute_out = forms.ChoiceField  (required = False, label = 'Минута отъезда',   choices    = MINUTES)
  second_out = forms.ChoiceField  (required = False, label = 'Секунда отъезда',  choices    = SECONDS)
  odometer   = forms.IntegerField (required = True,  label = 'Текущий пробег',   min_value  = 0)
  comment    = forms.CharField    (required = False, label = 'Комментарий',      max_length = 100)

class AddFuelTypeForm (forms.ModelForm):
  '''Добавление типа топлива'''
  class Meta (object):
    model  = FuelTypes
    fields = [
      'name',
      'comment',
    ]

  name    = forms.CharField (required = True,  label = 'Название',    max_length = 30)
  comment = forms.CharField (required = False, label = 'Комментарий', max_length = 100, widget = forms.Textarea)

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

class AddTravelForm (forms.ModelForm):
  '''Добавление поездки'''
  class Meta (object):
    model = Travels
    fields = [
      'point_start',
      'point_end',
      'distance',
      'comment',
    ]

  points       = forms.CharField    (label = 'Точки маршрута',   max_length = 0, required = False)
  point_start  = forms.CharField    (label = 'Выезд',            max_length = 100)
  point_end    = forms.CharField    (label = 'Назначение',       max_length = 100)

  start        = forms.CharField    (label = 'Выезда',           max_length = 0, required = False)
  end          = forms.CharField    (label = 'Прибытия',         max_length = 0, required = False)

  year_start   = forms.IntegerField (label = 'Год выезда',       min_value = 1966, initial = 2017)
  month_start  = forms.ChoiceField  (label = 'Месяц выезда',     choices = MONTHS)
  day_start    = forms.ChoiceField  (label = 'День выезда',      choices = DAYS)
  hour_start   = forms.ChoiceField  (label = 'Час выезда',       choices = HOURS)
  minute_start = forms.ChoiceField  (label = 'Минута выезда',    choices = MINUTES)
  second_start = forms.ChoiceField  (label = 'Секунда выезда',   choices = SECONDS)

  year_end     = forms.IntegerField (label = 'Год прибытия',     min_value = 1966, initial = 2017)
  month_end    = forms.ChoiceField  (label = 'Месяц прибытия',   choices = MONTHS)
  day_end      = forms.ChoiceField  (label = 'День прибытия',    choices = DAYS)
  hour_end     = forms.ChoiceField  (label = 'Час прибытия',     choices = HOURS)
  minute_end   = forms.ChoiceField  (label = 'Минута прибытия',  choices = MINUTES)
  second_end   = forms.ChoiceField  (label = 'Секунда прибытия', choices = SECONDS)

  distance     = forms.IntegerField (label = 'Расстояние', min_value = 0)
  comment      = forms.CharField    (label = 'Комментарий', max_length = 100, required = False)

