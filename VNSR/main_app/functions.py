from django.contrib       import auth
from calend_app.functions import get_month_text, create_calend_month, get_now
from menu_app.functions   import create_menu_user, create_menu_app

def default_context (request, app = None):
	'''
		Создаёт контекст для шаблона сайта по умолчанию
	'''
	username = auth.get_user (request).username
	context = {
		'username':   username,
		'month_text': get_month_text (),
		'calend':     create_calend_month (),
		'now':        get_now (),
	}
	context ['items'] = create_menu_user (username)
	return context
