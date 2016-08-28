from django.shortcuts     import render, redirect
from main_app.views       import is_user, default_context
from menu_app.functions   import create_menu_app
from django               import forms
from calend_app.functions import get_now
from datetime             import date, time, timedelta
from .models              import WorkPlane

# Create your views here.
def set_work_plane (request):
	'''
		Устанавливает новый план смен
	'''
	if not is_user (request): return redirect ('/')
	if request.POST:
		date_start = date (int (request.POST ['start_year']), int (request.POST ['start_month']), 1)
		date_end = date_start
		while date_end.month == date_start.month:
			date_end += timedelta (days = 1)
		date_end -= timedelta (days = 1)
		for i in range (1, 9):
			if request.POST ['code_%s' % str (i)] != '':
				plane = WorkPlane (
					date_start  = date_start,
					date_end    = date_end,
					code        = request.POST ['code_%s' % str (i)],
					start       = time (int (request.POST ['start_hour_%s' % str (i)]), int (request.POST ['start_minute_%s' % str (i)])),
					end         = time (int (request.POST ['end_hour_%s'   % str (i)]), int (request.POST ['end_minute_%s'   % str (i)])),
					break_day   = request.POST ['break_day_%s'   % str (i)],
					break_night = request.POST ['break_night_%s' % str (i)]
				)
				plane.save ()
		return redirect ('/metro')
	else:
		page = 'metro/add_work_plane.html'
		context = default_context (request)
		context ['now'] = get_now ()
		return render (request, page, context)

def index (request):
	'''
		Стартовая страница приложения
	'''
	if not is_user (request): return redirect ('/')
	page = 'metro/index.html'
	context = default_context (request)
	context ['items'] = create_menu_app ('metro')
	return render (request, page, context)
