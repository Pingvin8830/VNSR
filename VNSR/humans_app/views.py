from datetime                           import date
from django.shortcuts                   import render, redirect
from django.template.context_processors import csrf
from .forms                             import HumanForm
from .models                            import Humans
from main_app.views                     import is_user, default_context
from menu_app.functions                 import create_menu_app

# Create your views here.
def add_human (request):
  '''Добавляет человека'''
  if not is_user (request): return redirect ('/')
  context = default_context (request)
  if request.POST:
    form = HumanForm (request.POST)
    if form.is_valid ():
      data = form.cleaned_data
      human = form.save (commit = False)
      try:    human.birthday = date (int (data ['birthday_y']), int (data ['birthday_m']), int (data ['birthday_d']))
      except: human.birthday = None
      try:    human.deadday  = date (int (data ['deadday_y']), int (data ['deadday_m']), int (data ['deadday_d']))
      except: human.deadday  = None
      human.save ()
    return display_all (request)
  page             = 'humans/add_edit_human.html'
  context.update (csrf (request))
  context ['form'] = HumanForm
  context ['type'] = 'add'
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

def edit_human (request, id):
  '''Редактирование информации о человеке'''
  page    = 'humans/add_edit_human.html'
  context = default_context (request)
  if request.POST:
    form = HumanForm (request.POST)
    if form.is_valid ():
      data = form.cleaned_data
      human = form.save (commit = False)
      try:    human.birthday = date (int (data ['birthday_y']), int (data ['birthday_m']), int (data ['birthday_d']))
      except: human.birthday = None
      try:    human.deadday  = date (int (data ['deadday_y']), int (data ['deadday_m']), int (data ['deadday_d']))
      except: human.deadday  = None
      human.id = id
      human.save ()
      return display_all (request)
  context.update (csrf (request))
  human = Humans.objects.get (id = id)
  data  = {}
  for attr in human.__dict__:
    data [attr] = human.__dict__ [attr]
  try:    data ['birthday_y'] = data ['birthday'].year
  except: data ['birthday_y'] = None
  try:    data ['birthday_m'] = data ['birthday'].month
  except: data ['birthday_m'] = None
  try:    data ['birthday_d'] = data ['birthday'].day
  except: data ['birthday_d'] = None
  try:    data ['deadday_y'] = data ['deadday'].year
  except: data ['deadday_y'] = None
  try:    data ['deadday_m'] = data ['deadday'].month
  except: data ['deadday_m'] = None
  try:    data ['deadday_d'] = data ['deadday'].day
  except: data ['deadday_d'] = None
  form = HumanForm (data)
  context ['form'] = form
  context ['type'] = 'edit'
  context ['id']   = id
  return render (request, page, context)

def index (request):
  '''Стартовое представление'''
  if not is_user (request): return redirect ('/')
  page              = 'humans/index.html'
  context           = default_context (request)
  context ['items'] = create_menu_app ('humans')
  return render (request, page, context)

