from django.shortcuts   import render
from main_app.views     import is_user, default_context
from menu_app.functions import create_menu_app

# Create your views here.

def index (request):
  '''Стартовая страница приложения'''
  if not is_user (request): return redirect ('/')
  page    = 'car/index.html'
  context = default_context (request)
  context ['items'] = create_menu_app ('car')
  return render (request, page, context)

