from datetime             import date, time, timedelta
from django               import forms
from django.shortcuts     import render, redirect
from .forms               import AddPayslipForm
from .models              import WorkPlane, ShedulePlane, SheduleReal, CodesPayslip, Payslip, PayslipDetails, Rate
from calend_app.functions import get_now, get_month_text
from calend_app.models    import Signs
from menu_app.functions   import create_menu_app
from main_app.views       import is_user, default_context

# Create your views here.

def calculation (request):
  '''Расчеты за месяц'''
  if not is_user (request): return redirect ('/')
  if request.POST:
    context = default_context (request)
    page    = 'metro/calculation.html'
    month = int (request.POST ['month'])
    if month == 1:
      date_start_avans = date (
        int (request.POST ['year']) - 1,
        12,
        16
      )
      days = 31
      while True:
        try:
          date_end_avans = date (
            int (request.POST ['year']) - 1,
            12,
            days
          )
          break
        except:
          days -= 1
    else:
      date_start_avans = date (
        int (request.POST ['year']),
        int (request.POST ['month']) - 1,
        16
      )
      days = 31
      while True:
        try:
          date_end_avans = date (
            int (request.POST ['year']),
            int (request.POST ['month']) - 1,
            days
          )
          break
        except:
          days -= 1
    context ['test'] = '%s %s' % (date_start_avans, date_end_avans)
    return render (request, page, context)
  else:
    return case_month (request, href = 'calculation')
  return redirect ('/')

def case_period (request):
  '''Запрашивает период'''
  if not is_user (request): return redirect ('/')
  context = default_context (request)
  page    = 'metro/case_period.html'
  return render (request, page, context)

def display_tabel (request):
  '''Отображение табеля'''
  if not is_user (request): return redirect ('/')
  context = default_context (request)
  page    = 'metro/display_tabel.html'
  if request.POST:
    start = date (
      int (request.POST ['start_year']),
      int (request.POST ['start_month']),
      int (request.POST ['start_day'])
    )
    day = int (request.POST ['end_day'])
    while True:
      try:
        end = date (
          int (request.POST ['end_year']),
          int (request.POST ['end_month']),
          day
        )
        break
      except:
        day -= 1
    context ['start']  = start
    context ['end']    = end
    context ['shifts'] = []
    data        = start
    norma       = 0
    akk_hours   = 0
    akk_night   = 0
    akk_holiday = 0
    akk_sick    = 0
    while end >= data:
      signs = Signs.objects.get (data = data)
      if   signs.work:  norma += 8
      elif signs.short: norma += 7
      try:
        shift = SheduleReal.objects.get (data = data)
      except:
        shift = SheduleReal (data = data, start = time (0, 0, 0), end = time (0, 0, 0), break_day = 0, break_night = 0)
      shift.if_sick ()
      shift.hours   ()
      shift.night   ()
      shift.holiday ()
      context ['shifts'].append (shift)
      akk_hours += shift.hours
      akk_night += shift.night
      akk_holiday += shift.holiday
      if shift.sick: akk_sick += 1
      data += timedelta (days = 1)
    context ['akk_hours']   = akk_hours
    context ['akk_night']   = akk_night
    context ['akk_holiday'] = akk_holiday
    context ['akk_sick']    = akk_sick
    context ['norma']       = norma
    return render (request, page, context)
  else:
    return case_period (request)

def control_payslip (request, id):
  '''Проверка расчётного листка'''
  if not is_user (request): return redirect ('/')
  payslip                         = Payslip.objects.get (id = id)
  context                         = default_context (request)
  context ['control_payslip']     = payslip.control ()
  context ['control_details']     = payslip.control_details ()
  context ['differences_payslip'] = payslip.differences ()
  context ['differences_details'] = payslip.differences_details ()
  for key, value in context ['control_payslip'].items ():
    if value: context ['is_error'] = True
  for key, value in context ['control_details'].items ():
    if value: context ['is_error'] = True
  for key, value in context ['differences_payslip'].items ():
    if value: context ['is_difference'] = True
  for key, value in context ['differences_details'].items ():
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
    form = AddPayslipForm (request.POST)
    if form.is_valid ():
      payslip = form.save (commit = False)
      payslip.period = date (
        int (form.cleaned_data ['year']),
        int (form.cleaned_data ['month']),
        1)
      payslip.save ()
    context = default_context (request)
    return redirect ('/metro/display_payslip_details/%d' % payslip.id)
  return redirect ('/metro')

def display_payslip (request, payslip = None):
  '''Отображает расчетный листок'''
  if not is_user (request): return redirect ('/')
  if request.POST:
    context = default_context (request)
    if not payslip:
      try:
        payslip = Payslip.objects.get (period = '%s-%s-01' % (str (request.POST ['year']), str (request.POST ['month'])))
        page    = 'metro/display_payslip.html'
        payslip.print_edit ()
      except:
        payslip = Payslip ()
        page    = 'metro/add_payslip.html'
        context ['form'] = AddPayslipForm
    else:
        page    = 'metro/display_payslip.html'
        payslip.print_edit ()
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
    return case_month (request, href = 'payslip')

def case_month (request, href):
  '''Выбор месяца'''
  if not is_user (request): redirect ('/')
  page = 'metro/case_month.html'
  context = default_context (request)
  context ['href'] = href
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

