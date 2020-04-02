from django import forms
from sheduler import models

class Add(forms.ModelForm):
  class Meta:
    model = models.Humans
    fields = ['name']
    error_messages = {
      'name': {
        'unique': 'Такой человек уже есть',
      },
    }

class Update(forms.ModelForm):
  class Meta:
    model = models.Humans
    fields = ['id', 'name']
    error_messages = {
      'name': {
        'unique': 'Такой человек уже есть',
      },
    }

