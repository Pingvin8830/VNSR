from django.shortcuts import render, redirect
from django.contrib   import auth

# Create your views here.
def case_page (request):
	'''
		Выбор стартовой страницы
	'''
	print ()
	print ("'" + str (auth.get_user (request).username) + "'")
	print ()

	if auth.get_user (request).username != '':
		return redirect ('/calend/')
	else:
		return redirect ('/auth/login/')
