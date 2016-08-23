from django.shortcuts import render, redirect
from .functions       import create_calend_month, get_now, get_month_text, create_month_signs_form
from main_app.views   import is_user, default_context
from datetime         import date
from .models          import Signs

# Create your views here.
def display_calend_year (request, year = get_now ().year):
	'''
		Отображает календарь на год
	'''
	if not is_user: return redirect ('/')
	year    = int (year)
	page    = 'calend/year.html'
	context = default_context (request, 'calend')
	context ['calend_year']      = year
	context ['calend_prev_year'] = year - 1
	context ['calend_next_year'] = year + 1
	context ['calend_comments']  = Signs.objects.raw ('SELECT * FROM signs WHERE data BETWEEN "%s-01-01" AND "%s-12-31" AND comment != ""' % (str (year), str (year)))
	for month in range (1, 13):
		context ['calend_month_%s' % str (month)] = create_calend_month (year, month)
	return render (request, page, context)
	
def display_signs_month (request, year, month):
	'''
		Отображает настройку дней месяца
	'''
	if not is_user: return redirect ('/')
	year    = int (year)
	month   = int (month)
	page    = 'calend/month.html'
	context = default_context (request, 'calend')
	context ['form_signs']        = create_month_signs_form (year, month, 1)
	context ['form_signs']       += create_month_signs_form (year, month, 2)
	context ['calend_year']       = year
	context ['calend_month']      = month
	context ['calend_month_text'] = get_month_text (year, month)
	return render (request, page, context)

def set_signs_month (request, year, month):
	'''
		Сохраняет выбранные признаки дней месяца в БД
	'''
	if not is_user: return redirect ('/')
	year  = int (year)
	month = int (month)
	if request.POST:
		for day in range (1, 32):
			try:
				data = date (year, month, day)
			except:
				break
			try:
				signs = Signs.objects.get (data = data)
			except:
				signs = Signs (data = data)
			signs.work    = (request.POST [str(data)] == 'work')
			signs.week    = (request.POST [str(data)] == 'week')
			signs.holiday = (request.POST [str(data)] == 'holiday')
			signs.short   = (request.POST [str(data)] == 'short')
			signs.save ()
	return redirect ('/calend/%s' % year)
