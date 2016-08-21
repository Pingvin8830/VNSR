from django.shortcuts import render, redirect
from django.contrib   import auth
from menu_app         import models as menu

# Create your views here.
def is_user (request):
	'''
		Проверка авторизации
	'''
	return auth.get_user (request).username != ''

def default_context (request):
	'''
		Возвращает контекст по умолчанию
	'''
	context = {
		'username': auth.get_user (request).username,
		'items':    menu.Items.objects.raw ('SELECT * FROM items i, items_users u WHERE u.user="%s" AND i.id = u.item_id' % auth.get_user (request).username)
	}
	return context

def index (request):
	'''
	'''
	if not is_user (request): return redirect ('/auth/login/')
	page = 'main.html'
	context = default_context (request)
	return render (request, page, context)
