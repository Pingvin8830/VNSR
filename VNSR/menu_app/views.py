from django.shortcuts import render, redirect
from main_app.views   import default_context, is_user
from django.contrib   import auth
from .models          import Users
from .functions       import create_menu_app

# Create your views here.
def index (request):
	'''
		Стартовая страница приложения
	'''
	page              = 'menu/index.html'
	context           = default_context (request)
	context ['items'] = create_menu_app ('menu')
	return render (request, page, context)

def case_user (request):
	'''
		Выбор пользователя
	'''
	page                   = 'menu/case_user.html'
	context                = default_context (request)
	context ['menu_users'] = Users.objects.all ()
	return render (request, page, context)

def case_app (request):
	'''
		Выбор приложения
	'''
	page              = 'menu/case_app.html'
	context           = default_context (request)
	return render (request, page, context)
