from django.shortcuts import render, redirect
from main_app.views   import default_context, is_user
from django.contrib   import auth
from .models          import Users, ItemsMenu, AppMenu, ItemsApp
from .functions       import create_menu_app

# Create your views here.
def set_user (request):
	'''
		Сохраняет пользователей
	'''
	if not is_user (request): return redirect ('/')
	if request.POST:
		for user in Users.objects.all ():
			if 'delete_%s' % str (user.id) in request.POST:
				user.delete ()
				continue
			user.name = request.POST ['name_%s' % str (user.id)]
			user.save ()
		return redirect ('/menu')
	else:
		return display_user (request)

def display_user (request):
	'''
		Отображает пользователей
	'''
	page = 'menu/display_user.html'
	context = default_context (request)
	context ['menu_users'] = Users.objects.all ()
	return render (request, page, context)

def add_user (request):
	'''
		Добавляет пользователя
	'''
	if not is_user (request): return redirect ('/')
	if request.POST:
		user = Users (name = request.POST ['name'])
		user.save ()
		return redirect ('/menu')
	else:
		page = 'menu/add_user.html'
		context = default_context (request)
		return render (request, page, context)

def add_item (request):
	'''
		Добавляет новой действие приложения
	'''
	if is_user (request): redirect ('/')
	if request.POST:
		item = ItemsApp (text = request.POST ['text'], href = request.POST ['href'])
		item.save ()
		return redirect ('/menu')
	else:
		page = 'menu/add_item.html'
		context = default_context (request)
		return render (request, page, context)

def set_item (request):
	'''
		Сохраняет действия приложений
	'''
	if not is_user (request): return redirect ('/')
	if request.POST:
		for item in ItemsApp.objects.all ():
			if 'delete_%s' % str (item.id) in request.POST:
				item.delete ()
				continue
			item.text = request.POST ['text_%s' % str (item.id)]
			item.href = request.POST ['href_%s' % str (item.id)]
			item.save ()
		return redirect ('/menu')
	else:
		return display_item (request)

def display_item (request):
	'''
		Отображает действия приложений
	'''
	if not is_user (request): return redirect ('/')
	page    = 'menu/display_item.html'
	context = default_context (request)
	context ['menu_items'] = ItemsApp.objects.all ()
	return render (request, page, context)

def add_app (request):
	'''
		Добавление приложения
	'''
	if not is_user (request): return redirect ('/')
	if request.POST:
		app = ItemsMenu (app = request.POST ['app'], text = request.POST ['text'])
		app.save ()
		return redirect ('/menu')
	else:
		page = 'menu/add_app.html'
		context = default_context (request)
		return render (request, page, context)

def set_app (request):
	'''
		Сохраняет приложения
	'''
	if not is_user (request): return redirect ('/')
	if request.POST:
		for app in ItemsMenu.objects.all ():
			if 'delete_%s' % str (app.id) in request.POST:
				app.delete ()
				continue
			app.app  = request.POST ['app_%s'  % str (app.id)]
			app.text = request.POST ['text_%s' % str (app.id)]
			app.save ()
		return redirect ('/menu')
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
