from django           import forms
from .lists           import SEXS
from .models          import Humans
from calend_app.lists import MONTHS, DAYS

# Create your forms here.

class HumanForm (forms.ModelForm):
  '''Добавление человека'''
  class Meta (object):
    model = Humans
    fields = ['family', 'name', 'father_name', 'comment', 'first_family', 'sex']

  family       = forms.CharField    (label = 'Фамилия',         max_length = 20)
  first_family = forms.CharField    (label = 'Девичья фамилия', max_length = 20, required = False)
  name         = forms.CharField    (label = 'Имя',             max_length = 20)
  father_name  = forms.CharField    (label = 'Отчество',        max_length = 20, required = False)
  sex          = forms.ChoiceField  (label = 'Пол',   choices = SEXS,            required = False)
  birthday_d   = forms.ChoiceField  (label = 'День',  choices = DAYS,            required = False)
  birthday_m   = forms.ChoiceField  (label = 'Месяц', choices = MONTHS,          required = False)
  birthday_y   = forms.IntegerField (label = 'Год',                              required = False)
  deadday_d    = forms.ChoiceField  (label = 'День',  choices = DAYS,            required = False)
  deadday_m    = forms.ChoiceField  (label = 'Месяц', choices = MONTHS,          required = False)
  deadday_y    = forms.IntegerField (label = 'Год',                              required = False)
  comment      = forms.CharField    (widget = forms.Textarea,                    required = False)

