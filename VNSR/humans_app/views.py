from django.shortcuts   import render, redirect
from main_app.views     import is_user, default_context
from menu_app.functions import create_menu_app

# Create your views here.
def index (request):
  '''Стартовое представление'''
  if not is_user (request): return redirect ('/')
  page = 'humans/index.html'
  context = default_context (request)
  context ['items'] = create_menu_app ('humans')
  return render (request, page, context)

