from django.shortcuts import render, redirect
from main_app.views   import default_context, is_user
from django.contrib   import auth
from .models          import Users, ItemsMenu, AppMenu, ItemsApp
from .functions       import create_menu_app

# Create your views here.
def set_app (request):
	'''
		Сохраняет приложения
	'''
	if not is_user (request): return redirect ('/')
	print ()
	if request.POST:
		for app in ItemsMenu.objects.all ():
			if 'delete_%s' % str (app.id) in request.POST:
				app.delete ()
				continue
			app.app  = request.POST ['app_%s'  % str (app.id)]
			app.text = request.POST ['text_%s' % str (app.id)]
			app.save ()
		return redirect ('/')
	else:
		return display_app (request)

def display_app (request):
	'''
		Отображает приложения
	'''
	if not is_user (request): return redirect ('/')
	menu_apps = ItemsMenu.objects.all ()
	page      = 'menu/display_app.html'
	context   = default_context (request)
	context   ['menu_apps'] = menu_apps
	return render (request, page, context)

def display_app_menu (request):
	'''
		Отображает меню приложения
	'''
	if not is_user (request): return redirect ('/')
	if 'app' in request.POST:
		app = ItemsMenu.objects.get (app = request.POST ['app'])
		page    = 'menu/display_app.html'
		context = default_context (request)
		context ['menu_items'] = ItemsApp.objects.all ()
		context ['installed'] = []
		for item in AppMenu.objects.filter (app_id = app.id):
			context ['installed'].append (item.item_id)
		context ['menu_app']   = request.POST ['app']
		return render (request, page, context)
	else:
		return case_app (request)

def index (request):
	'''
		Стартовая страница приложения
	'''
	if not is_user (request): redirect ('/')
	page                 = 'menu/index.html'
	context              = default_context (request)
	context ['items']    = create_menu_app ('menu')
	context ['menu_app'] = None
	return render (request, page, context)

def case_user (request):
	'''
		Выбор пользователя
	'''
	if not is_user (request): return redirect ('/')
	page                   = 'menu/case_user.html'
	context                = default_context (request)
	context ['menu_users'] = Users.objects.all ()
	return render (request, page, context)

def case_app (request):
	'''
		Выбор приложения
	'''
	if not is_user (request): return redirect ('/')
	page                  = 'menu/case_app.html'
	context               = default_context (request)
	context ['menu_apps'] = ItemsMenu.objects.all ()
	return render (request, page, context)
