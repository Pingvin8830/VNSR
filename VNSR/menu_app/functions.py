from .models import ItemsMenu, ItemsApp

def create_menu_user (username):
	'''
		Создаёт меню для пользователя
	'''
	sql = '                                       \
		SELECT *                                    \
		FROM   user_menu um, users u, items_menu im \
		WHERE  um.user_id = u.id                    \
			AND  um.item_id = im.id                   \
			AND  u.name     = "%s"                    \
	' % username
	return ItemsMenu.objects.raw (sql)

def create_menu_app (app):
	'''
		Создаёт меню для приложения
	'''
	sql = '                                           \
		SELECT *                                        \
		FROM   items_app ia, app_menu am, items_menu im \
		WHERE  am.app_id  = im.id                       \
			AND  am.item_id = ia.id                       \
			AND  im.app     = "%s"                        \
	' % app
	return ItemsApp.objects.raw (sql)

