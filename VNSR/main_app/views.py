from django.shortcuts   import render, redirect
from auth_app.functions import is_user
from .functions         import default_context

# Create your views here.
def index (request):
	'''
		Отображение стартовой страницы
	'''
	if not is_user (request):	return redirect ('/auth/login/')
	page    = 'main/index.html'
	context = default_context (request)
	return render (request, page, context)
