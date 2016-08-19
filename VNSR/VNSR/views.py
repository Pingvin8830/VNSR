from django.shortcuts import render, redirect
from django.contrib   import auth

# Create your views here.
def is_user (request):
	'''
		Проверка авторизации
	'''
	return auth.get_user (request).username != ''

def case_page (request):
	'''
		Выбор стартовой страницы
	'''
	if is_user (request):
		return redirect ('/calend/')
	else:
		return redirect ('/auth/login/')
