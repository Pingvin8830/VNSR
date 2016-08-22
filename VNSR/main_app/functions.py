from django.contrib       import auth
from menu_app.models      import Items
from calend_app.functions import get_month_text, create_calend_month, get_now

def default_context (request):
	'''
		Создаёт контекст для шаблона сайта по умолчанию
	'''
	username = auth.get_user (request).username
	sql      = ' \
		SELECT *   \
		FROM   items i, items_users u \
		WHERE  i.id   = u.item_id     \
			AND  u.user = "%s"' % username
	context = {
		'username':   username,
		'items':      Items.objects.raw (sql),
		'month_text': get_month_text (),
		'calend':     create_calend_month (),
		'now':        get_now (),
	}
	return context

