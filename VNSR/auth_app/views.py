from django.shortcuts import render, redirect
from django.contrib   import auth

# Create your views here.

def login (request):
	'''
		Авторизация
	'''
	if request.POST:
		username = request.POST ['username']
		password = request.POST ['password']
		user     = auth.authenticate (username = username, password = password)
		if user is not None:
			auth.login (request, user)
			return redirect ('/')
		else:
			return render (request, 'auth/login.html', {})
	else:
		return render (request, 'auth/login.html', {})

def logout (request):
	'''
		Выход
	'''
	auth.logout (request)
	return redirect ('/')

