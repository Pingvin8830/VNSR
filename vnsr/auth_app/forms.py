from django import forms

class Login(forms.Form):
  login = forms.CharField(label='Логин')
  password = forms.CharField(label='Пароль', widget=forms.PasswordInput)
