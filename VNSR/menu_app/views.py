from django.shortcuts import render, redirect
from main_app.views   import default_context, is_user
from django.contrib   import auth
from .models          import Users

# Create your views here.
def case_user (request):
	'''
		Выбор пользователя
	'''
	page                   = 'menu/case_user.html'
	context                = default_context (request)
	context ['menu_users'] = Users.objects.all ()
	return render (request, page, context)
