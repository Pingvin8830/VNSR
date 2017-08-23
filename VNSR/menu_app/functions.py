from .models import ItemsApp, UserMenu

def create_menu_user (user):
  ''' Создаёт меню для пользователя'''
  user_menu = UserMenu.objects.filter (user = user).order_by ('item__text')
  items = []
  for item in user_menu: items.append (item.item)
  return items

def create_menu_app (app):
  '''Создаёт меню для приложения'''
  sql = '                                           \
    SELECT *                                        \
    FROM   items_app ia, app_menu am, items_menu im \
    WHERE  am.app_id  = im.id                       \
      AND  am.item_id = ia.id                       \
      AND  im.app     = "%s"                        \
    ORDER BY ia.text                                \
  ' % app
  return ItemsApp.objects.raw (sql)

