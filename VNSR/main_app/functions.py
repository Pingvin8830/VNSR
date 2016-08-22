from django.contrib       import auth
from menu_app.models      import Items, ItemsApps
from calend_app.functions import get_month_text, create_calend_month, get_now

def default_context (request, type_menu, app_name = None):
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
	if type_menu == 'user':
		menu = create_menu_user (username)
	elif type_menu == 'app':
		menu = create_menu_app (app_name)
	context ['items'] = menu
	return context

def create_menu_user (username):
	'''
		Создаёт меня для пользователя
	'''
	sql = '                             \
		SELECT *                          \
		FROM   items i, items_users u     \
		WHERE  i.id   = u.item_id         \
			AND  u.user = "%s"' % username
	return Items.objects.raw (sql)

def create_menu_app (app):
	'''
		Создаёт меню для приложения
	'''
	sql = '                        \
		SELECT *                     \
		FROM   items i, items_apps a \
		WHERE  i.id = a.item_id      \
			AND  a.app = "%s"' % app
	return Items.objects.raw (sql)
