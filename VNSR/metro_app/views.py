from django.shortcuts     import render, redirect
from main_app.views       import is_user, default_context
from menu_app.functions   import create_menu_app
from django               import forms
from calend_app.functions import get_now, get_month_text
from datetime             import date, time, timedelta
from .models              import WorkPlane, ShedulePlane, SheduleReal, CodesPayslip, Payslip, PayslipDetails

# Create your views here.

def control_payslip (request, id):
  '''Проверка расчётного листка'''
  if not is_user (request): return redirect ('/')
  payslip                         = Payslip.objects.get (id = id)
  context                         = default_context (request)
  context ['control_payslip']     = payslip.control ()
  context ['control_details']     = payslip.control_details ()
  context ['differences_payslip'] = payslip.differences ()
  for key, value in context ['control_payslip'].items ():
    if value: context ['is_error'] = True
  for key, value in context ['differences_payslip'].items ():
    if value: context ['is_difference'] = True
  page                = 'metro/control_payslip.html'
  return render (request, page, context)

def add_details (request, id):
  '''Добавляет строку в расчетный листок'''
  if not is_user (request): return redirect ('/')
  if request.POST:
    payslip = Payslip.objects.get (id = id)
    details = PayslipDetails (payslip = payslip, code = CodesPayslip.objects.get (id = request.POST ['code']), period = request.POST ['year'] + '-' + request.POST ['month'] + '-01', summa = request.POST ['summa'], count = request.POST ['count'])
    details.save ()
    return display_payslip (request, payslip)
  else:
    page = 'metro/add_details.html'
    context = default_context (request)
    context ['codes'] = CodesPayslip.objects.all ().order_by ('code')
    context ['id']    = id
    return render (request, page, context)
  return redirect ('/')

def add_payslip (request):
  '''Добавляет расчетный листок в БД'''
  if not is_user (request): return redirect ('/')
  if request.POST:
    if not request.POST ['rotate_dolg']: request.POST.rotate_dolg = None
    payslip = Payslip (period = request.POST ['year'] + '-' + request.POST ['month'] + '-01', division = request.POST ['division'], post = request.POST ['post'], rate = request.POST ['rate'].replace (',', '.'), begin_dolg = request.POST ['begin_dolg'].replace (',', '.'), rotate_dolg = request.POST ['rotate_dolg'].replace (',', '.'), end_dolg = request.POST ['end_dolg'].replace (',', '.'), income = request.POST ['income'].replace (',', '.'), consumption = request.POST ['consumption'].replace (',', '.'), paying = request.POST ['paying'].replace (',', '.'))
    payslip.save ()
    context = default_context (request)
    return redirect ('/metro/display_payslip_details/%d' % payslip.id)
  return redirect ('/metro')

def display_payslip (request, payslip = None):
  '''Отображает расчетный листок'''
  if not is_user (request): return redirect ('/')
  if request.POST:
    if not payslip:
      try:
        payslip = Payslip.objects.get (period = '%s-%s-01' % (str (request.POST ['year']), str (request.POST ['month'])))
        page    = 'metro/display_payslip.html'
        payslip.print_edit ()
      except:
        payslip = Payslip ()
        page    = 'metro/add_payslip.html'
    else:
        page    = 'metro/display_payslip.html'
        payslip.print_edit ()
    context = default_context (request)
    context ['payslip']     = payslip
    details = PayslipDetails.objects.filter (payslip = payslip.id).order_by ('code', 'period')
    context ['incomes']      = []
    context ['consumptions'] = []
    context ['others']       = []
    for i in details:
      i.print_edit ()
      if   i.type == 'i': context ['incomes'].append      (i)
      elif i.type == 'c': context ['consumptions'].append (i)
      elif i.type == 'o': context ['others'].append       (i)
    return render (request, page, context)
  else:
    return case_month (request)
  
def case_month (request):
  '''Выбор месяца'''
  if not is_user (request): redirect ('/')
  page = 'metro/case_month.html'
  context = default_context (request)
  return render (request, page, context)

def add_codes_payslip (request):
  '''Добавляет новый код НДФЛ'''
  if not is_user (request): return redirect ('/')
  if request.POST:
    code = CodesPayslip (code = request.POST ['code'], name = request.POST ['name'], type = request.POST ['type'])
    code.save ()
    return redirect ('/metro')
  else:
    page = 'metro/add_codes_payslip.html'
    context = default_context (request)
    return render (request, page, context)

def set_codes_payslip (request):
  '''Сохраняет коды НДФЛ'''
  if not is_user (request): return redirect ('/')
  if request.POST:
    for code_payslip in CodesPayslip.objects.all ():
      if 'delete_%s' % str (code_payslip.id) in request.POST:
        code_payslip.delete ()
        continue
      code_payslip.code = request.POST ['code_%s' % str (code_payslip.id)]
      code_payslip.name = request.POST ['name_%s' % str (code_payslip.id)]
      code_payslip.type = request.POST ['type_%s' % str (code_payslip.id)]
      code_payslip.save ()
    return redirect ('/metro')
  else:
    return display_codes_payslip (request)

def display_codes_payslip (request):
  '''Отображает коды НДФЛ'''
  if not is_user (request): return redirect ('/')
  page = 'metro/display_codes_payslip.html'
  context = default_context (request)
  context ['codes_payslip'] = CodesPayslip.objects.all ().order_by ('code')
  return render (request, page, context)

def set_shift (request):
  '''Устанавливает смену'''
  if not is_user (request): return redirect ('/')
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
        break_day   = int  (request.POST ['break_day']),
        break_night = int  (request.POST ['break_night']),
        lanch       = bool (request.POST ['lanch']),
        vacation    = False,
        sick        = False
      )
    shift.save ()
    return redirect ('/metro')
  else:
    page    = 'metro/add_shift.html'
    context = default_context (request)
    context ['days'] = range (1, 32)
    return render (request, page, context)

def set_shedule (request, data):
  '''Устанавливает новый график'''
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
  '''Запрашивает новый график'''
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
  '''Устанавливает новый план смен'''
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
  '''Стартовая страница приложения'''
  if not is_user (request): return redirect ('/')
  page = 'metro/index.html'
  context = default_context (request)
  context ['items'] = create_menu_app ('metro')
  return render (request, page, context)
