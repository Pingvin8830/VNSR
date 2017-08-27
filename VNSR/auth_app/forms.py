from django import forms

# Create your forms here

class LoginForm (forms.Form):

  login    = forms.CharField (label = 'Имя пользователя')
  password = forms.CharField (label = 'Пароль', widget = forms.PasswordInput)
