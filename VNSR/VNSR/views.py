from django.shortcuts import render, redirect
from django.contrib   import auth

# Create your views here.
def is_user (request):
	'''
		Проверка авторизации
	'''
	return auth.get_user (request).username != ''

def index (request):
	'''
	'''
	if not is_user (request): return redirect ('/auth/login/')
	page = 'main.html'
	context = {}
	return render (request, page, context)
