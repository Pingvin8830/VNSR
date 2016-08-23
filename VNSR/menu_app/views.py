from django.shortcuts import render, redirect
from main_app.views   import default_context, is_user
from django.contrib   import auth

# Create your views here.
def display_menu (request, menu_username = None):
	'''
		Отображение меню пользователя
	'''
	if not is_user: redirect ('/')
	if not menu_username:
		menu_username = auth.get_user (request).username
	page    = 'menu/index.html'
	context = default_context (request)
	return render (request, page, context)
