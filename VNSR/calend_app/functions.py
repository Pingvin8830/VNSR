from datetime import date, timedelta
from .models  import Signs

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

def create_calend_month (year = get_now ().year, month = get_now ().month):
	'''
		Составляет календарь на месяц
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

def create_month_signs_form (year, month, part):
	'''
		Создаёт одну часть формы настройки дней месяца
	'''
	form = '<tr><td></td>'
	if part == 1:
		start = 1
		end   = 17
	elif part == 2:
		start = 17
		end   = 32
		form += '</tr><tr height="20"></tr><tr><td></td>'
	for day in range (start, end):
		try:
			name = date (year, month, day)
		except:
			break
		form += '<td>%s</td>' % str (day)
	form += '</tr><tr><td>Рабочий</td>'
	for day in range (start, end):
		try:
			name = date (year, month, day)
		except:
			break
		form += '<td><input type="radio" name="%s" value="work"></td>' % str (name)
	form += '</tr><tr><td>Выходной</td>'
	for day in range (start, end):
		try:
			name = date (year, month, day)
		except:
			break
		form += '<td><input type="radio" name="%s" value="week"></td>' % str (name)
	form += '</tr><tr><td>Праздничный</td>'
	for day in range (start, end):
		try:
			name = date (year, month, day)
		except:
			break
		form += '<td><input type="radio" name="%s" value="holiday"></td>' % str (name)
	form += '</tr><tr><td>Сокращённый</td>'
	for day in range (start, end):
		try:
			name = date (year, month, day)
		except:
			break
		form += '<td><input type="radio" name="%s" value="short"></td>' % str (name)
	form += '</tr>'
	return form
