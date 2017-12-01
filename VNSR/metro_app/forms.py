from django           import forms
from .lists           import PAYSLIP_CODE_TYPES
from .models          import PayslipCodes, Payslip, SheduleReal
from calend_app.lists import DAYS, HOURS, MINUTES, MONTHS

# Create your forms here.

class AddPayslipCodeForm (forms.ModelForm):
  '''Добавление кода НДФЛ'''
  class Meta (object):
    model = PayslipCodes
    fields = [
      'code',
      'name',
      'type'
    ]

  code = forms.CharField   (label = 'Код',      max_length =  8, initial = 'Новый код')
  name = forms.CharField   (label = 'Название', max_length = 50, initial = 'Название')
  type = forms.ChoiceField (label = 'Тип',      choices = PAYSLIP_CODE_TYPES)

class AddPayslipForm (forms.ModelForm):
  '''Расчетный листок из ОК'''
  class Meta ():
    model = Payslip
    fields = ['division', 'post', 'rate', 'begin_dolg', 'rotate_dolg', 'end_dolg', 'income', 'consumption', 'paying']

  month       = forms.ChoiceField  (label = 'Месяц',         choices   = MONTHS)
  year        = forms.IntegerField (label = 'Год',           max_value = 9999, initial = 2017)
  rate        = forms.DecimalField (label = 'Ставка',        min_value = 0, max_digits = 9,  decimal_places = 2)
  begin_dolg  = forms.DecimalField (label = 'Долг сначала',  min_value = 0, max_digits = 11, decimal_places = 2)
  rotate_dolg = forms.DecimalField (label = 'Обороты долга', min_value = 0, max_digits = 11, decimal_places = 2)
  end_dolg    = forms.DecimalField (label = 'Долг в конце',  min_value = 0, max_digits = 11, decimal_places = 2)
  income      = forms.DecimalField (label = 'Начислено',     min_value = 0, max_digits = 11, decimal_places = 2)
  paying      = forms.DecimalField (label = 'Пересылка',     min_value = 0, max_digits = 11, decimal_places = 2)
  consumption = forms.DecimalField (label = 'Удержано',      min_value = 0, max_digits = 11, decimal_places = 2)

class AddSheduleRealForm (forms.ModelForm):
  '''Добавление рабочей смены'''
  class Meta (object):
    model = SheduleReal
    fields = [
      'lanch',
      'break_day',
      'break_night',
      'vacation',
      'sick',
    ]

  data         = forms.CharField    (label = 'Дата',       required = False, max_length = 0)
  data_year    = forms.IntegerField (label = 'Год',        required = True,  min_value = 0, max_value = 9999, initial = 2017)
  data_month   = forms.ChoiceField  (label = 'Месяц',      required = True,  choices = MONTHS)
  data_day     = forms.ChoiceField  (label = 'День',       required = True,  choices = DAYS)
  start        = forms.CharField    (label = 'Начало',     required = False, max_length = 0)
  start_hour   = forms.ChoiceField  (label = 'Часы',       required = False, choices = HOURS,   initial = 9)
  start_minute = forms.ChoiceField  (label = 'Минуты',     required = False, choices = MINUTES, initial = 0)
  end          = forms.CharField    (label = 'Окончание',  required = False, max_length = 0)
  end_hour     = forms.ChoiceField  (label = 'Часы',       required = False, choices = HOURS,   initial = 18)
  end_minute   = forms.ChoiceField  (label = 'Минуты',     required = False, choices = MINUTES, initial = 0)
  lanch        = forms.BooleanField (label = 'Обед',       required = False, initial = True)
  break_day    = forms.IntegerField (label = 'День',       required = False, min_value = 0, max_value = 32, initial = 2)
  break_night  = forms.IntegerField (label = 'Ночь',       required = False, min_value = 0, max_value = 16, initial = 0)
  delay        = forms.CharField    (label = 'Задержка',   required = False, max_length = 0)
  delay_hour   = forms.ChoiceField  (label = 'Часы',       required = False, choices = HOURS)
  delay_minute = forms.ChoiceField  (label = 'Минуты',     required = False, choices = MINUTES)
  vacation     = forms.BooleanField (label = 'Отпуск',     required = False, initial = False)
  sick         = forms.BooleanField (label = 'Больничный', required = False, initial = False)

