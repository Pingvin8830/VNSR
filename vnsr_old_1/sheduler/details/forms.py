from django import forms
from sheduler import models

class Add(forms.ModelForm):
  class Meta:
    model = models.Details
    fields = ['task', 'location', 'human']
#    error_messages = {
#      'name': {
#        'unique': 'Такой человек уже есть',
#      },
#    }

class Update(forms.ModelForm):
  class Meta:
    model = models.Details
    fields = ['id', 'task', 'location', 'human', 'is_done']
#    error_messages = {
#      'name': {
#        'unique': 'Такой человек уже есть',
#      },
#    }

