from django           import forms
from .models          import Humans
from calend_app.lists import MONTHS, DAYS

# Create your forms here.

class AddHumanForm (forms.ModelForm):
  '''Добавление человека'''
  class Meta (object):
    model = Humans
    fields = ['family', 'name', 'father_name', 'comment']

  family      = forms.CharField    (label = 'Фамилия',  max_length = 20)
  name        = forms.CharField    (label = 'Имя',      max_length = 20)
  father_name = forms.CharField    (label = 'Отчество', max_length = 20, required = False)
  birthday_d  = forms.ChoiceField  (label = 'День',  choices = DAYS,     required = False)
  birthday_m  = forms.ChoiceField  (label = 'Месяц', choices = MONTHS,   required = False)
  birthday_y  = forms.IntegerField (label = 'Год',                       required = False)
  deadday_d   = forms.ChoiceField  (label = 'День',  choices = DAYS,     required = False)
  deadday_m   = forms.ChoiceField  (label = 'Месяц', choices = MONTHS,   required = False)
  deadday_y   = forms.IntegerField (label = 'Год',                       required = False)
  comment     = forms.CharField    (widget = forms.Textarea,             required = False)

