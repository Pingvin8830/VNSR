from django import forms
from sheduler import models

class Add(forms.ModelForm):
  class Meta:
    model = models.Tasks
    fields = ['name']
    error_messages = {
      'name': {
        'unique': 'Такая задача уже есть',
      },
    }

class Update(forms.ModelForm):
  class Meta:
    model = models.Tasks
    fields = ['id', 'name']
    error_messages = {
      'name': {
        'unique': 'Такая задача уже есть',
      },
    }

