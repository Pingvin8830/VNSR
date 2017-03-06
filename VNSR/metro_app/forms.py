from django  import forms
from .models import Payslip
from calend_app.functions import MONTHS

# Create your forms here.

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
