from datetime                           import datetime
from decimal                            import Decimal
from django.shortcuts                   import render, redirect
from django.template.context_processors import csrf
from .forms                             import AddAzsForm, AddCheckPointForm, AddFuelTypeForm, AddRefuelForm, AddTravelForm
from .models                            import Azs, CheckPoints, FuelTypes, PayTypes, Refuels, Travels
from calend_app.forms                   import CalendLabels
from main_app.views                     import is_user, default_context
from menu_app.functions                 import create_menu_app

# Create your views here.

def index (request):
  '''Стартовая страница приложения'''
  if not is_user (request): return redirect ('/')
  page    = 'car/index.html'
  context = default_context (request)
  context ['items'] = create_menu_app ('car')
  return render (request, page, context)

def add_azs (request):
  '''Добавляет АЗС'''
  if not is_user (request): return redirect ('/')
  if request.POST:
    form = AddAzsForm (request.POST)
    if form.is_valid ():
      azs = form.save ()
    return index (request)
  else:
    page = 'car/add_azs.html'
    context = default_context (request)
    context.update (csrf (request))
    context ['form'] = AddAzsForm
    return render (request, page, context)

def add_check_point (request, travel_id):
  '''Добавление контрольной точки поездки'''
  if not is_user (request): return redirect ('/')
  if request.POST:
    form = AddCheckPointForm (request.POST)
    if form.is_valid ():
      data = form.cleaned_data
      check_point = form.save (commit = False)
      check_point.travel = Travels.objects.get (id = travel_id)
      try:
        check_point.date_time_in = datetime (
            int (data ['year_in']),
            int (data ['month_in']),
            int (data ['day_in']),
            int (data ['hour_in']),
            int (data ['minute_in']),
            int (data ['second_in'])
          )
      except:
        check_point.date_time_in = None
      try:
        check_point.date_time_out = datetime (
          int (data ['year_out']),
          int (data ['month_out']),
          int (data ['day_out']),
          int (data ['hour_out']),
          int (data ['minute_out']),
          int (data ['second_out'])
        )
      except:
        check_point.date_time_out = None
      check_point.save ()
      return index (request)
  else:
    page                      = 'car/add_check_point.html'
    context                   = default_context (request)
    context.update (csrf (request))
    context ['travel']        = Travels.objects.get (id = travel_id)
    context ['form']          = AddCheckPointForm
    context ['calend_labels'] = CalendLabels
    return render (request, page, context)
  return index (request)

def add_fuel_type (request):
  '''Добавление типа топлива'''
  if not is_user (request): return redirect ('/')
  if request.POST:
    form = AddFuelTypeForm (request.POST)
    if form.is_valid ():
        fule_type = form.save ()
    return index (request)
  else:
    page = 'car/add_fuel_type.html'
    context = default_context (request)
    context.update (csrf (request))
    context ['form'] = AddFuelTypeForm
    return render (request, page, context)

def add_refuel (request):
  '''Добавление чека с заправки'''
  if not is_user (request): return redirect ('/')
  if request.POST:
    form = AddRefuelForm (request.POST)
    if form.is_valid ():
      data = form.cleaned_data
      refuel = form.save (commit = False)
      refuel.date_time = datetime (
        int (data ['year']),
        int (data ['month']),
        int (data ['day']),
        int (data ['hour']),
        int (data ['minute']),
        int (data ['second']),
      )
      refuel.save ()
    return index (request)
  else:
    page    = 'car/add_refuel.html'
    context = default_context (request)
    context.update (csrf (request))
    context ['form'] = AddRefuelForm
    return render (request, page, context)

def add_travel (request):
  '''Добавление поездки'''
  if not is_user (request): return redirect ('/')
  if request.POST:
    form = AddTravelForm (request.POST)
    if form.is_valid ():
      data = form.cleaned_data
      travel = form.save (commit = False)
      travel.date_time_start = datetime (
        int (data ['year_start']),
        int (data ['month_start']),
        int (data ['day_start']),
        int (data ['hour_start']),
        int (data ['minute_start']),
        int (data ['second_start'])
      )
      travel.date_time_end = datetime (
        int (data ['year_end']),
        int (data ['month_end']),
        int (data ['day_end']),
        int (data ['hour_end']),
        int (data ['minute_end']),
        int (data ['second_end'])
      )
      travel.save ()
    return index (request)
  else:
    page = 'car/add_travel.html'
    context = default_context (request)
    context.update (csrf (request))
    context ['form']          = AddTravelForm
    context ['calend_labels'] = CalendLabels
    return render (request, page, context)

def display_azss (request):
  '''Отображение автозаправочных станций'''
  if not is_user (request): return redirect ('/')
  page             = 'car/display_azss.html'
  context          = default_context (request)
  context ['azss'] = Azs.objects.all ().order_by ('company', 'name')
  return render (request, page, context)

def display_check_points (request, travel_id):
  '''Отображение контрольных точек поездки'''
  if not is_user (request): return redirect ('/')
  travel = Travels.objects.get (id = travel_id)
  page                     = 'car/display_check_points.html'
  context                  = default_context (request)
  context ['check_points'] = CheckPoints.objects.filter (travel = travel)
  context ['travel']       = travel
  return render (request, page, context)

def display_fuel_types (request):
  '''Отображение типов топлива'''
  if not is_user (request): return redirect ('/')
  page = 'car/display_fuel_types.html'
  context = default_context (request)
  context ['fuel_types'] = FuelTypes.objects.all ().order_by ('name')
  return render (request, page, context)

def display_pay_types (request):
  '''Отображение типов оплаты'''
  if not is_user (request): return redirect ('/')
  page = 'car/display_pay_types.html'
  context = default_context (request)
  context ['pay_types'] = PayTypes.objects.all ().order_by ('name')
  return render (request, page, context)

def display_refuels (request):
  '''Отображение заправок автомобиля'''
  if not is_user (request): return redirect ('/')
  page = 'car/display_refuels.html'
  context = default_context (request)
  context ['refuels'] = []
  for refuel in Refuels.objects.all ().order_by ('date_time'):
    try:
      refuel.man_cons = round ((refuel.fuel_count * 100) / refuel.odometer, 2)
    except:
      refuel.man_cons = Decimal (0)
    refuel.color = {}
    if   (refuel.man_cons    - refuel.consumption > 0.5): refuel.color ['auto'] = 'bad'
    elif (refuel.consumption - refuel.man_cons    > 0.5): refuel.color ['auto'] = 'bad'
    elif (refuel.consumption - refuel.man_cons    < 0.3) and \
         (refuel.man_cons    - refuel.consumption < 0.3): refuel.color ['auto'] = 'ok'
    if   refuel.man_cons > 10: refuel.color ['man'] = 'bad'
    elif refuel.man_cons < 8:  refuel.color ['man'] = 'ok'
    if   refuel.cost - (refuel.fuel_count * refuel.price) > 0.01: refuel.color ['cost'] = 'bad'
    elif (refuel.fuel_count * refuel.price) - refuel.cost > 0.01: refuel.color ['cost'] = 'ok'
    context ['refuels'].append (refuel)
  return render (request, page, context)

def display_travels (request):
  '''Отображение поездок'''
  if not is_user (request): return redirect ('/')
  page    = 'car/display_travels.html'
  context = default_context (request)
  context ['travels'] = []
  for travel in Travels.objects.all ():
    travel.start = str (travel.date_time_start)
    travel.end   = str (travel.date_time_end)
    context ['travels'].append (travel)
  return render (request, page, context)

