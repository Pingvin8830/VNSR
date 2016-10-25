from django.shortcuts     import render, redirect
from main_app.views       import is_user, default_context
from menu_app.functions   import create_menu_app
from django               import forms
from calend_app.functions import get_now, get_month_text
from datetime             import date, time, timedelta
from .models              import WorkPlane, ShedulePlane, SheduleReal

# Create your views here.
def set_shift (request):
	'''
		Устанавливает смену
	'''
	if not is_user (request): return redirect ('/')
	print ()
	if request.POST:
		data  = date (
			int (request.POST ['year']),
			int (request.POST ['month']),
			int (request.POST ['day'])
		)
		try:
			shift = SheduleReal.objects.get (data = data)
		except:
			shift = SheduleReal (
				data = data,
				start = time (
					int (request.POST ['start_hour']),
					int (request.POST ['start_minute'])
				),
				end = time (
					int (request.POST ['end_hour']),
					int (request.POST ['end_minute'])
				),
				delay = time (
					int (request.POST ['delay_hour']),
					int (request.POST ['delay_minute'])
				),
				break_day   = int (request.POST ['break_day']),
				break_night = int (request.POST ['break_night']),
				vacation    = False,
				sick        = False
			)
		shift.save ()
		return redirect ('/metro')

	else:
		page    = 'metro/add_shift.html'
		context = default_context (request)
		return render (request, page, context)

def set_shedule (request, data):
	'''
		Устанавливает новый график
	'''
	if not is_user (request): return redirect ('/')
	data = date (int (data [0:4]), int (data [5:7]), int (data [8:10]))
	if request.POST:
		month = data.month
		while data.month == month:
			if 'shift_%s' % str (data.day) in request.POST:
				shedule = ShedulePlane (data = data, shift = request.POST ['shift_%s' % str (data.day)])
				shedule.save ()
			data += timedelta (days = 1)
		return redirect ('/metro')
	else:
		return redirect ('/')

def add_shedule (request, data):
	'''
		Запрашивает новый график
	'''
	if not is_user (request): return redirect ('/')
	page = 'metro/add_shedule.html'
	context = default_context (request)
	context ['metro_month'] = get_month_text (data.year, data.month)
	context ['metro_dates'] = []
	date_end = data
	while data.month == date_end.month:
		context ['metro_dates'].append (date_end)
		date_end += timedelta (days = 1)
	sql = '                                             \
		SELECT *                                          \
		FROM   work_plane                                 \
		WHERE  "%s-%s-15" BETWEEN date_start AND date_end \
	' % (str (data.year), str (data.month))
	context ['metro_work_plane'] = WorkPlane.objects.raw (sql)
	context ['metro_data'] = data
	return render (request, page, context)

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
		return add_shedule (request, date_start)
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
