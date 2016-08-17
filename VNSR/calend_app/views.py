from django.shortcuts import render
from .models          import Signs
from datetime         import date,   timedelta

# Create your views here.
def display_calend (request, year, month):
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
		context ['table'] += "<td align='right'></td>"

	while data.weekday () != 0:
		context ['table'] += "<td align='right'><font color='"
		signs = Signs.objects.get (data = data)
		if signs.week:
			context ['table'] += "red'>"
		elif signs.holiday:
			context ['table'] += "green'>"
		elif signs.short:
			context ['table'] += "yellow'>"
		else:
			context ['table'] += "black'>"
		context ['table'] += str (data.day)
		context ['table'] += "</font></td>"
		data              += timedelta (days = 1)

	context ['table'] += "</tr>"

	while data.month == month:
		context ['table'] += "<tr>"
		context ['table'] += "<td align='right'><font color='"
		signs = Signs.objects.get (data = data)
		if signs.week:
			context ['table'] += "red'>"
		elif signs.holiday:
			context ['table'] += "green'>"
		elif signs.short:
			context ['table'] += "yellow'>"
		else:
			context ['table'] += "black'>"
		context ['table'] += str (data.day)
		context ['table'] += "</font></td>"
		data += timedelta (days = 1)
		while data.weekday () != 0:
			if data.month == month:
				context ['table'] += "<td align='right'><font color='"
				signs = Signs.objects.get (data = data)
				if signs.week:
					context ['table'] += "red'>"
				elif signs.holiday:
					context ['table'] += "green'>"
				elif signs.short:
					context ['table'] += "yellow'>"
				else:
					context ['table'] += "black'>"
				context ['table'] += str (data.day)
				context ['table'] += "</font></td>"

			else:
				context ['table'] += "<td align='right'></td>"

			data += timedelta (days = 1)

		context ['table'] += "</tr>"
		
	return render (request, page, context)

