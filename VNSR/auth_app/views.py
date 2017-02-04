from django.contrib                     import auth
from django.template.context_processors import csrf
from django.shortcuts                   import render, redirect
from .forms                             import LoginForm

# Create your views here.

def login (request):
  '''Авторизация'''
  page = 'auth/login.html'
  context = {}
  context.update (csrf (request))
  context ['form'] = LoginForm
  if request.POST:
    form = LoginForm (request.POST)
    if form.is_valid ():
      form_data = form.cleaned_data
      user = auth.authenticate (username = form_data ['login'], password = form_data ['password'])
      if user is not None:
        auth.login (request, user)
      return redirect ('/')
    else:
      return redirect ('/')
    return redirect ('/')
  else:
    return render (request, page, context)

def logout (request):
  '''Выход'''
  auth.logout (request)
  return redirect ('/')

