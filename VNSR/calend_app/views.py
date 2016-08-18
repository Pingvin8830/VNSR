from django.shortcuts import render, get_object_or_404
from .models          import Signs
from datetime         import date,   timedelta

MONTH = [
	'Январь',
	'Февраль',
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
]

NOW = date (1, 1, 1).today ()

# Create your views here.

def get_td_class (signs):
	'''
		Получает признаки дня и возвращает класс для шаблона
	'''
	if   signs.work:    return 'work'
	elif signs.week:    return 'week'
	elif signs.holiday: return 'holiday'
	elif signs.short:   return 'short'
	else:               return None

def create_cell_calend (data):
	'''
		Формирует корректную ячейку календаря
	'''
	try:
		signs = Signs.objects.get (data = data)
	except:
		signs = Signs (data = data)
	cell  = "<td class='%s'>" % get_td_class (signs)
	cell += str (data.day)
	cell += "</td>"
	return cell

def default_context (year, month):
	'''
		Формирует контекст по умолчанию
	'''
	prev_month = month - 1
	next_month = month + 1
	prev_year  = year
	next_year  = year
	if prev_month < 1:
		prev_month += 12
		prev_year  -= 1
	if next_month > 12:
		next_month -= 12
		next_year  += 1
	context = {
		'month':      month,
		'month_text': MONTH [month - 1],
		'year':       year,
		'prev_month': prev_month,
		'next_month': next_month,
		'prev_year':  prev_year,
		'next_year':  next_year,
	}
	return context

def display_calend (request, year = NOW.year, month = NOW.month):
	'''
		Отображение календаря
	'''
	year    = int (year)
	month   = int (month)
	page    = 'calend/index.html'
	context = default_context (year, month)
	context ['table']      = "<tr>"
	context ['now']        = "Сегодня: " + str (NOW)

	data  = date (year, month, 1)
	entry = data.weekday ()
	for i in range (entry):
		context ['table'] += "<td></td>"

	while data.weekday () != 0:
		context ['table'] += create_cell_calend (data)
		data              += timedelta (days = 1)

	context ['table'] += "</tr>"

	while data.month == month:
		context ['table'] += "<tr>" + create_cell_calend (data)
		data += timedelta (days = 1)
		while data.weekday () != 0:
			if data.month == month:
				context ['table'] += create_cell_calend (data)
				
			else:
				context ['table'] += "<td></td>"

			data += timedelta (days = 1)

		context ['table'] += "</tr>"
		
	return render (request, page, context)

def display_signs (request, year = date (1, 1, 1).today ().year, month = date (1, 1, 1).today ().month):
	'''
		Настройка календаря
	'''
	year    = int (year)
	month   = int (month)
	page    = 'calend/display_signs.html'
	context = default_context (year, month)
	context ['table'] = "<tr><td></td>"

	data = date (year, month, 1)
	while data.month == month:
		try:
			context ['table'] += create_cell_calend (data)
		except:
			context ['table'] += "<td>%s</td>" % data.day
		data += timedelta (days = 1)

	context ['table'] += "</tr><tr><td>Рабочий</td>"
	data = date (year, month, 1)
	while data.month == month:
		context ['table'] += "<td><input type='radio' name='%s' value='work'" % str (data)
		try:
			if Signs.objects.get (data = data).work:
				context ['table'] += "checked"
		except:
			context ['table'] += "checked"
		context ['table'] += "></td>"
		data += timedelta (days = 1)

	context ['table'] += "</tr><tr><td>Выходной</td>"
	data = date (year, month, 1)
	while data.month == month:
		context ['table'] += "<td><input type='radio' name='%s' value='week'" % str (data)
		try:
			if Signs.objects.get (data = data).week:
				context ['table'] += "checked"
		except:
			context ['table'] += ""
		context ['table'] += "></td>"
		data += timedelta (days = 1)

	context ['table'] += "</tr><tr><td>Праздничный</td>"
	data = date (year, month, 1)
	while data.month == month:
		context ['table'] += "<td><input type='radio' name='%s' value='holiday'" % str (data)
		try:
			if Signs.objects.get (data = data).holiday:
				context ['table'] += "checked"
		except:
			context ['table'] += ""
		context ['table'] += "></td>"
		data += timedelta (days = 1)

	context ['table'] += "</tr><tr><td>Сокращённый</td>"
	data = date (year, month, 1)
	while data.month == month:
		context ['table'] += "<td><input type='radio' name='%s' value='short'" % str (data)
		try:
			if Signs.objects.get (data = data).short:
				context ['table'] += "checked"
		except:
			context ['table'] += ""
		context ['table'] += "></td>"
		data += timedelta (days = 1)
	context ['table'] += "</tr>"

	return render (request, page, context)

def set_signs (request, year, month):
	'''
		Записывает изменения в БД
	'''
	year  = int (year)
	month = int (month)

	data = date (year, month, 1)
	while data.month == month:
		signs = Signs (data = data)
		signs.work    = False
		signs.week    = False
		signs.holiday = False
		signs.short   = False
		if   request.POST [str (data)] == 'work':    signs.work    = True
		elif request.POST [str (data)] == 'week':    signs.week    = True
		elif request.POST [str (data)] == 'holiday': signs.holiday = True
		elif request.POST [str (data)] == 'short':   signs.short   = True
		signs.save () 
		data += timedelta (days = 1)

	return display_calend (request, year, month)
