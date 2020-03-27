from django           import forms
from .lists           import PAYSLIP_CODE_TYPES
from .models          import PayslipCodes, PayslipDetails, Payslips, SheduleReal
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

class AddPayslipDetailsForm (forms.Form):
  '''Добавляет строчку в расчётный листок'''

  code_income       = forms.ChoiceField  (required = False, label = 'Вид начисления', choices = [(None, '----------')] + [(p.id, p.code + ' ' + p.name) for p in PayslipCodes.objects.filter (type = 'i').order_by ('code')])
  code_other        = forms.ChoiceField  (required = False, label = '',               choices = [(None, '----------')] + [(p.id, p.code + ' ' + p.name) for p in PayslipCodes.objects.filter (type = 'o').order_by ('code')])
  code_consumption  = forms.ChoiceField  (required = False, label = 'Вид удержания',  choices = [(None, '----------')] + [(p.id, p.code + ' ' + p.name) for p in PayslipCodes.objects.filter (type = 'c').order_by ('code')])
  period            = forms.CharField    (required = False, label = 'Период', max_length = 0)
  month_income      = forms.ChoiceField  (required = False, label = 'Месяц',  choices = MONTHS)
  month_other       = forms.ChoiceField  (required = False, label = 'Месяц',  choices = MONTHS)
  month_consumption = forms.ChoiceField  (required = False, label = 'Месяц',  choices = MONTHS)
  year_income       = forms.IntegerField (required = False, label = 'Год',    min_value = 0, max_value = 9999, initial = 2017)
  year_other        = forms.IntegerField (required = False, label = 'Год',    min_value = 0, max_value = 9999, initial = 2017)
  year_consumption  = forms.IntegerField (required = False, label = 'Год',    min_value = 0, max_value = 9999, initial = 2017)
  summa_income      = forms.DecimalField (required = False, label = 'Сумма',  min_value = 0, max_digits = 11, decimal_places = 2, initial = 0)
  summa_other       = forms.DecimalField (required = False, label = 'Сумма',  min_value = 0, max_digits = 11, decimal_places = 2, initial = 0)
  summa_consumption = forms.DecimalField (required = False, label = 'Сумма',  min_value = 0, max_digits = 11, decimal_places = 2, initial = 0)
  count_income      = forms.DecimalField (required = False, label = 'Ед.',    min_value = 0, max_digits = 11, decimal_places = 2, initial = 0)
  count_other       = forms.DecimalField (required = False, label = 'Ед.',    min_value = 0, max_digits = 11, decimal_places = 2, initial = 0)
  count_consumption = forms.DecimalField (required = False, label = 'Ед.',    min_value = 0, max_digits = 11, decimal_places = 2, initial = 0)

class AddPayslipForm (forms.ModelForm):
  '''Расчетный листок из ОК'''
  class Meta ():
    model = Payslips
    fields = [
      'division',
      'post',
      'rate',
      'begin_dolg',
      'rotate_dolg',
      'end_dolg',
      'income',
      'consumption',
      'paying'
    ]

  month       = forms.ChoiceField  (label = 'Месяц',                               choices    = MONTHS)
  year        = forms.IntegerField (label = 'Год',                                 max_value  = 9999, initial = 2017)
  division    = forms.CharField    (label = 'Подразделение:',                      max_length = 50,   initial = 'Оптовая продажа алкоголя')
  post        = forms.CharField    (label = 'Должность:',                          max_length = 50,   initial = 'Кассир-оператор ПК')
  rate        = forms.DecimalField (label = 'Оклад/Тариф:',                        min_value  = 0, max_digits = 9,  decimal_places = 2, initial = 207)
  begin_dolg  = forms.DecimalField (label = 'Долг за работником в начале месяца ', min_value  = 0, max_digits = 11, decimal_places = 2, initial = 0)
  rotate_dolg = forms.DecimalField (label = 'Выплачено в течение месяца',          min_value  = 0, max_digits = 11, decimal_places = 2, initial = 0)
  end_dolg    = forms.DecimalField (label = 'Долг за работником на конец месяца',  min_value  = 0, max_digits = 11, decimal_places = 2, initial = 0)
  income      = forms.DecimalField (label = 'Начислено',                           min_value  = 0, max_digits = 11, decimal_places = 2)
  consumption = forms.DecimalField (label = 'Удержано',                            min_value  = 0, max_digits = 11, decimal_places = 2)
  paying      = forms.DecimalField (label = 'Пересылка в банк',                    min_value  = 0, max_digits = 11, decimal_places = 2)

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

