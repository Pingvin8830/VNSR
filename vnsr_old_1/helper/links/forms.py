from django import forms

from helper import models

class Add(forms.ModelForm):
  class Meta:
    model = models.Links
    fields = ['url', 'name']

