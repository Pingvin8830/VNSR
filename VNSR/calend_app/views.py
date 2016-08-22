from django.shortcuts import render, redirect
from .functions       import create_calend_month, get_now
from main_app.views   import is_user, default_context

# Create your views here.
def display_calend_year (request, year = get_now ().year):
	'''
		Отображает календарь на год
	'''
	if not is_user: return redirect ('/')
	year    = int (year)
	page    = 'calend/year.html'
	context = default_context (request, 'app', 'calend_app')
	context ['calend_year']      = year
	context ['calend_prev_year'] = year - 1
	context ['calend_next_year'] = year + 1
	for month in range (1, 13):
		context ['calend_month_%s' % str (month)] = create_calend_month (year, month)
	return render (request, page, context)
	
def display_signs_month (request, year, month):
	'''
		Отображает настройку дней месяца
	'''
	year  = int (year)
	month = int (month)
	page  = 'calend/month.html'
	context = default_context (request, 'app', 'calend_app')
	context ['calend_year'] = year
	context ['calend_month'] = month
	return render (request, page, context)
