from django.shortcuts   import render, redirect
from .models            import Humans
from main_app.views     import is_user, default_context
from menu_app.functions import create_menu_app

# Create your views here.
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

