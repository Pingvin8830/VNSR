from datetime import date, time
from django import forms

from calend import models, constants

class AddMark(forms.ModelForm):
  class Meta:
    model = models.Marks
    fields = ['sign', 'comment']

  year   = forms.IntegerField(min_value=1960, max_value=date.today().year+1, label='Год')
  month  = forms.IntegerField(min_value=1, max_value=12, label='Месяц')
  day    = forms.IntegerField(min_value=1, max_value=31, label='День')
  hour   = forms.IntegerField(min_value=0, max_value=24, required=False, label='Час')
  minute = forms.IntegerField(min_value=0, max_value=59, required=False, label='Минута')

  def __init__(self, *args, **kwargs):
    super().__init__(*args, **kwargs)
    year = self.fields['year']
    month = self.fields['month']
    day = self.fields['day']
    hour = self.fields['hour']
    minute = self.fields['minute']
    year.widget = forms.Select()
    month.widget = forms.Select()
    day.widget = forms.Select()
    hour.widget = forms.Select()
    minute.widget = forms.Select()
    hour.widget.choices = [(None, '')]
    minute.widget.choices = [(None, '')]
    for i in range(year.min_value, year.max_value+1): year.widget.choices.append((i, str(i)))
    for i in range(1, 13): month.widget.choices.append((i, constants.MONTHS_TEXT[i]))
    for i in range(1, 32): day.widget.choices.append((i, str(i)))
    for i in range(24):
      add = str(i)
      if i < 10: add = '0' + add
      hour.widget.choices.append((i, add))
    for i in range(60):
      add = str(i)
      if i < 10: add = '0' + add
      minute.widget.choices.append((i, add))
    year.initial = date.today().year

  def save(self, *args, **kwargs):
    self.instance.date = date(self.cleaned_data['year'], self.cleaned_data['month'], self.cleaned_data['day'])
    if self.cleaned_data['hour'] != None:
      self.instance.time = time(self.cleaned_data['hour'], self.cleaned_data['minute'])
    return super().save(*args, **kwargs)

class AddSign(forms.ModelForm):
  class Meta:
    model = models.Signs
    fields = ['name']

