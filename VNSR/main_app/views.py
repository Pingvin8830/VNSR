from django.shortcuts import render, redirect
from django.contrib   import auth

def is_user (request):
	'''
		Проверка авторизации
	'''
	return auth.get_user (request).username != ''

def default_context ():
	'''
		Создаёт контекст для шаблона сайта по умолчанию
	'''
	context = {}
	return context

# Create your views here.
def index (request):
	'''
		Отображение стартовой страницы
	'''
	if not is_user (request):	return redirect ('/auth/login/')
	page    = 'main/index.html'
	context = default_context ()
	return render (request, page, context)
