from django  import forms
from .models import Cards

# Create your forms here.

class AddCardForm (forms.ModelForm):
  '''Счета хранения средств'''
  class Meta ():
    model = Cards
    fields = ['number', 'name', 'comment']

  number  = forms.CharField    (label = 'Номер карточки', max_length = 20, required = False)
  name    = forms.CharField    (label = 'Название',       max_length = 20)
  comment = forms.CharField    (label = 'Комментарий',    max_length = 100, required = False)
