from django import forms
from sheduler import models

class Add(forms.ModelForm):
  class Meta:
    model = models.Locations
    fields = ['name']
    error_messages = {
      'name': {
        'unique': 'Такое место уже есть',
      },
    }

class Update(forms.ModelForm):
  class Meta:
    model = models.Locations
    fields = ['id', 'name']
    error_messages = {
      'name': {
        'unique': 'Такое место уже есть',
      },
    }

