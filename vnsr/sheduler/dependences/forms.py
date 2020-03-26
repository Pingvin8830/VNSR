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

class Update(forms.ModelForm):
  class Meta:
    model = models.Dependences
    fields = ['id', 'detail', 'prev_detail']
#    error_messages = {
#      'name': {
#        'unique': 'Такой человек уже есть',
#      },
#    }

