from django.shortcuts import render
from .models          import Signs
from datetime         import date,   timedelta

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

def display_calend (request, year = date (1, 1, 1).today ().year, month = date (1, 1, 1).today ().month):
	'''
		Отображение календаря
	'''
	year       = int (year)
	month      = int (month)
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
	page    = 'main.html'
	context = {}
	context ['month_text'] = MONTH [month - 1]
	context ['year']       = year
	context ['prev_month'] = prev_month
	context ['next_month'] = next_month
	context ['prev_year']  = prev_year
	context ['next_year']  = next_year

	context ['table']      = "<tr>"
	data  = date (year, month, 1)
	entry = data.weekday ()
	for i in range (entry):
		context ['table'] += "<td></td>"

	while data.weekday () != 0:
		signs = Signs.objects.get (data = data)
		context ['table'] += "<td class='%s'>" % get_td_class (signs)
		context ['table'] += str (data.day)
		context ['table'] += "</td>"
		data              += timedelta (days = 1)

	context ['table'] += "</tr>"

	while data.month == month:
		context ['table'] += "<tr>"
		signs = Signs.objects.get (data = data)
		context ['table'] += "<td class='%s'>" % get_td_class (signs)
		context ['table'] += str (data.day)
		context ['table'] += "</td>"
		data += timedelta (days = 1)
		while data.weekday () != 0:
			if data.month == month:
				signs = Signs.objects.get (data = data)
				context ['table'] += "<td class='%s'>" % get_td_class (signs)
				context ['table'] += str (data.day)
				context ['table'] += "</td>"

			else:
				context ['table'] += "<td></td>"

			data += timedelta (days = 1)

		context ['table'] += "</tr>"
		
	return render (request, page, context)

