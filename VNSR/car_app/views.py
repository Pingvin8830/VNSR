from datetime                           import datetime
from django.shortcuts                   import render, redirect
from django.template.context_processors import csrf
from .forms                             import AddRefuelForm
from main_app.views                     import is_user, default_context
from menu_app.functions                 import create_menu_app

# Create your views here.

def index (request):
  '''Стартовая страница приложения'''
  if not is_user (request): return redirect ('/')
  page    = 'car/index.html'
  context = default_context (request)
  context ['items'] = create_menu_app ('car')
  return render (request, page, context)

def add_refuel (request):
  '''Добавление чека с заправки'''
  if not is_user (request): return redirect ('/')
  if request.POST:
    form = AddRefuelForm (request.POST)
    print ()
    print (form.is_valid ())
    print ()
    if form.is_valid ():
      data = form.cleaned_data
      refuel = form.save (commit = False)
      refuel.date_time = datetime (
        int (data ['year']),
        int (data ['month']),
        int (data ['day']),
        int (data ['hour']),
        int (data ['minute']),
        int (data ['second']),
      )
      refuel.save ()
    return index (request)
  else:
    page    = 'car/add_refuel.html'
    context = default_context (request)
    context.update (csrf (request))
    context ['form'] = AddRefuelForm
    return render (request, page, context)

