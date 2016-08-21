from django.shortcuts  import render, redirect
from django.contrib    import auth
from menu_app.models   import Items
from datetime          import date, timedelta
from calend_app.models import Signs

def get_now ():
	'''
		Возвращает значение текущей даты
	'''
	return date (1, 1, 1).today ()

def get_month_text (year = get_now ().year, month = get_now ().month):
	'''
		Возвращает текстовое значение месяца по числовому
	'''
	month = int (month)
	MONTH = (
		'Январь',
		'Фефраль',
		'Март',
		'Апрель',
		'Май',
		'Июнь',
		'Июль',
		'Август',
		'Сентябрь',
		'Октябрь',
		'Ноябрь',
		'Декабрь',
	)
	month_text  = MONTH [month - 1]
	month_text += ' %s г.' % year
	return month_text

def get_td_class (signs):
	'''
		Возвращает класс ячейки календаря
	'''
	if   signs.work:    return 'work'
	elif signs.week:    return 'week'
	elif signs.holiday: return 'holiday'
	elif signs.short:   return 'short'
	else:               return None

def create_cell_calend (data):
	'''
		Формирует ячейку календаря
	'''
	try:
		signs = Signs.objects.get (data = data)
	except:
		signs = Signs (data = data)
	cell  = '<td class="%s">' % get_td_class (signs)
	cell += str (data.day)
	cell += '</td>'
	return cell

def calend (year = get_now ().year, month = get_now ().month):
	'''
		Составляет календарь для сайта
	'''
	year   = int (year)
	month  = int (month)
	data   = date (year, month, 1)
	entry  = data.weekday ()
	calend = '<tr>'
	for i in range (entry):
		calend += '<td></td>'
	while data.weekday () != 0:
		calend += create_cell_calend (data)
		data   += timedelta (days = 1)
	calend += '</tr>'
	while data.month == month:
		calend += '<tr>' + create_cell_calend (data)
		data   += timedelta (days = 1)
		while data.weekday () != 0:
			if data.month == month:
				calend += create_cell_calend (data)
			else:
				calend += '<td></td>'
			data += timedelta (days = 1)
		calend += '</tr>'
	return calend

def is_user (request):
	'''
		Проверка авторизации
	'''
	return auth.get_user (request).username != ''

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
		'calend':     calend (),
	}
	return context

# Create your views here.
def index (request):
	'''
		Отображение стартовой страницы
	'''
	if not is_user (request):	return redirect ('/auth/login/')
	page    = 'main/index.html'
	context = default_context (request)
	return render (request, page, context)
