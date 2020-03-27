from django import forms
from sheduler import models

class Add(forms.ModelForm):
  class Meta:
    model = models.Dependences
    fields = ['detail', 'prev_detail']
#    error_messages = {
#      'name': {
#        'unique': 'Такой человек уже есть',
#      },
#    }

  detail = forms.ModelChoiceField(models.Details.objects.filter(is_done=False).order_by('task__name', 'location__name', 'human__name'), label='Задача')
  prev_detail = forms.ModelChoiceField(models.Details.objects.filter(is_done=False).order_by('task__name', 'location__name', 'human__name'), label='Предыдущая задача')

class Update(forms.ModelForm):
  class Meta:
    model = models.Dependences
    fields = ['id', 'detail', 'prev_detail']
#    error_messages = {
#      'name': {
#        'unique': 'Такой человек уже есть',
#      },
#    }

