from datetime                           import date
from django.shortcuts                   import render, redirect
from django.template.context_processors import csrf
from .forms                             import AddHumanForm
from .models                            import Humans
from main_app.views                     import is_user, default_context
from menu_app.functions                 import create_menu_app

# Create your views here.
def add_human (request):
  '''Добавляет человека'''
  if not is_user (request): return redirect ('/')
  context = default_context (request)
  if request.POST:
    form = AddHumanForm (request.POST)
    if form.is_valid ():
      data = form.cleaned_data
      human = form.save (commit = False)
      try:    human.birthday = date (int (data ['birthday_y']), int (data ['birthday_m']), int (data ['birthday_d']))
      except: human.birthday = None
      try:    human.deadday  = date (int (data ['deadday_y']), int (data ['deadday_m']), int (data ['deadday_d']))
      except: human.deadday  = None
      human.save ()
    return display_all (request)
  page             = 'humans/add_human.html'
  context.update (csrf (request))
  context ['form'] = AddHumanForm
  return render (request, page, context)

def display (request, id):
  '''Отображение одного человека'''
  if not is_user (request): return redirect ('/')
  page              = 'humans/display.html'
  context           = default_context (request)
  context ['human'] = Humans.objects.get (id = id)
  return render (request, page, context)

def display_all (request):
  '''Отображение всех людей'''
  if not is_user (request): return redirect ('/')
  page               = 'humans/display_all.html'
  context            = default_context (request)
  context ['humans'] = Humans.objects.filter (id__gte = 0).order_by ('family', 'name', 'father_name')
  return render (request, page, context)

def index (request):
  '''Стартовое представление'''
  if not is_user (request): return redirect ('/')
  page              = 'humans/index.html'
  context           = default_context (request)
  context ['items'] = create_menu_app ('humans')
  return render (request, page, context)

