from django.contrib     import auth
from django.shortcuts   import render, redirect
from .models            import Links, UserLinks
from main_app.functions import create_menu_app
from main_app.views     import is_user, default_context
from menu_app.models    import Users

# Create your views here.

def index (request):
  '''Стартовая страница приложения'''
  if not is_user (request): return redirect ('/')
  page    = 'hrefs/index.html'
  context = default_context (request)
  context ['items'] = create_menu_app ('hrefs')
  '''user  = Users.objects.get (name = auth.get_user (request).username)
  context ['links'] = Links.objects.filter (user = user)'''
  user = Users.objects.get (name = auth.get_user (request).username)
  user_links = UserLinks.objects.filter (user = user)
  links = []
  for user_link in user_links:
    links.append (user_link.link)
  context ['links'] = links
  return render (request, page, context)

