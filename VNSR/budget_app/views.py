from datetime                           import date, time
from django.shortcuts                   import render, redirect
from django.template.context_processors import csrf
from .forms                             import AddCardForm, AddDebetForm, AddDebetTypeForm, AddOrgForm, AddOrgTypeForm, CasePeriodForm
from .models                            import Cards, Debets, DebetTypes, Orgs, OrgTypes, Cheques, Credits
from main_app.views                     import is_user, default_context
from menu_app.functions                 import create_menu_app


# Create your views here.

def add_card (request):
  '''Добавляет новый счет хранения средств'''
  if not is_user (request): return redirect ('/')
  if request.POST:
    form = AddCardForm (request.POST)
    if form.is_valid ():
      form.save ()
    return display_cards (request)
  else:
    page    = 'budget/add_card.html'
    context = default_context (request)
    context.update (csrf (request))
    context ['form'] = AddCardForm
    return render (request, page, context)

def add_debet (request):
  '''Добавляет доход'''
  if not is_user (request): return redirect (request)
  if request.POST:
    form = AddDebetForm (request.POST)
    if form.is_valid ():
      data = form.cleaned_data
      debet = form.save (commit = False)
      debet.date = date (int (data ['year']), int (data ['month']),  int (data ['day']))
      debet.time = time (int (data ['hour']), int (data ['minute']), int (data ['second']))
      debet.save ()
      return display_debets (request)
  else:
    page = 'budget/add_debet.html'
    context = default_context (request)
    context.update (csrf (request))
    context ['form'] = AddDebetForm
    return render (request, page, context)

def add_debet_type (request):
  '''Добавляет новый тип дохода'''
  if not is_user (request): return redirect ('/')
  if request.POST:
    form = AddDebetTypeForm (request.POST)
    if form.is_valid ():
      form.save ()
    return display_debet_types (request)
  else:
    page = 'budget/add_debet_type.html'
    context = default_context (request)
    context.update (csrf (request))
    context ['form'] = AddDebetTypeForm
    return render (request, page, context)

def add_org (request):
  '''Добавлят организацию'''
  if not is_user (request): return redirect ('/')
  if request.POST:
    form = AddOrgForm (request.POST)
    if form.is_valid ():
      form.save ()
    return display_debets (request)
  else:
    page    = 'budget/add_org.html'
    context = default_context (request)
    context ['form'] = AddOrgForm
    return render (request, page, context)

def add_org_type (request):
  '''Добавляет новый тип организации'''
  if not is_user (request): return redirect ('/')
  if request.POST:
    form = AddOrgTypeForm (request.POST)
    if form.is_valid ():
      form.save ()
    return display_debets (request)
  else:
    page    = 'budget/add_org_type.html'
    context = default_context (request)
    context.update (csrf (request))
    context ['form'] = AddOrgTypeForm
    return render (request, page, context)

def case_period (request, func = 'debets'):
  '''Выбор периода'''
  page    = 'budget/case_period.html'
  context = default_context (request)
  context.update (csrf (request))
  context ['form'] = CasePeriodForm
  context ['func'] = func
  return render (request, page, context)

def set_cards (request):
  '''Сохранение изменений в счетах хранения средств'''
  if not is_user (request): return redirect ('/')
  if request.POST:
    for card in Cards.objects.all ():
      if 'delete_%d' % card.id in request.POST:
        card.delete ()
        continue
      card.number  = request.POST ['number_%d'  % card.id]
      card.name    = request.POST ['name_%d'    % card.id]
      card.comment = request.POST ['comment_%d' % card.id]
      card.save ()
  return display_cards (request)

def set_debet_types (request):
  '''Сохранение изменений в типах доходов'''
  if not is_user (request): return redirect ('/')
  if request.POST:
    for debet_type in DebetTypes.objects.all ():
      if 'delete_%d' % debet_type.id in request.POST:
        debet_type.delete ()
        continue
      debet_type.name    = request.POST ['name_%d'    % debet_type.id]
      debet_type.comment = request.POST ['comment_%d' % debet_type.id]
      debet_type.save ()
  return display_debet_types (request)

def set_org_types (request):
  '''Сохранение изменений в типах организаций'''
  if not is_user (request): return redirect ('/')
  if request.POST:
    for org_type in OrgTypes.objects.all ():
      if 'delete_%d' % org_type.id in request.POST:
        org_type.delete ()
        continue
      org_type.name    = request.POST ['name_%d' % org_type.id]
      org_type.comment = request.POST ['comment_%d' % org_type.id]
      org_type.save ()
  return display_org_types (request)

def display_cards (request):
  '''Вывод мест хранения средств'''
  if not is_user (request): return redirect ('/')
  page    = 'budget/display_cards.html'
  context = default_context (request)
  context ['cards'] = Cards.objects.all ()
  return render (request, page, context)

def display_credits (request):
  '''Отображение расходов'''
  if not is_user (request): return redirect ('/')
  if request.POST:
    form = CasePeriodForm (request.POST)
    if form.is_valid ():
      form_data = form.cleaned_data
      page = 'budget/display_credits.html'
      context = default_context (request)
      context ['template'] = form_data ['template']
      day = int (form_data ['day_start'])
      while True:
        try:
          date_start = date (
            int (form_data ['year_start']),
            int (form_data ['month_start']),
            day
          )
          break
        except:
          day -= 1
      day = int (form_data ['day_end'])
      while True:
        try:
          date_end = date (
            int (form_data ['year_end']),
            int (form_data ['month_end']),
            day
          )
          break
        except:
          day -= 1
    credits = Credits.objects.filter (cheque__date__gte = date_start, cheque__date__lte = date_end).order_by ('cheque__date')
    akkum = 0
    o = {}
    c = {}
    t = {}
    for credit in credits:
      if not credit.cost: credit.cost = credit.price * credit.count
      akkum += credit.cost
      if credit.cheque.org       not in o: o [credit.cheque.org]       = 0
      if credit.cheque.card      not in c: c [credit.cheque.card]      = 0
      if credit.product.category not in t: t [credit.product.category] = 0
      o [credit.cheque.org]       += credit.cost
      c [credit.cheque.card]      += credit.cost
      t [credit.product.category] += credit.cost
    orgs  = []
    cards = []
    types = []
    for org  in o: orgs.append  ({'name': org.name,  'summa': o [org]})
    for card in c: cards.append ({'name': card.name, 'summa': c [card]})
    for type in t: types.append ({'name': type.name, 'summa': t [type]})
    context ['date_start'] = date_start
    context ['date_end']   = date_end
    context ['credits']    = credits
    context ['akkum']      = akkum
    context ['orgs']       = orgs
    context ['cards']      = cards
    context ['types']      = types
    return render (request, page, context)
  else:
    return case_period (request, 'credits')

def display_debets (request):
  '''Отображение доходов'''
  if not is_user (request): return redirect ('/')
  if request.POST:
    form = CasePeriodForm (request.POST)
    if form.is_valid ():
      form_data = form.cleaned_data
      page      = 'budget/display_debets.html'
      context   = default_context (request)
      context ['template'] = form_data ['template']
      day = int (form_data ['day_start'])
      while True:
        try:
          date_start = date (
            int (form_data ['year_start']),
            int (form_data ['month_start']),
            day
          )
          break
        except:
          day -= 1
      day = int (form_data ['day_end'])
      while True:
        try:
          date_end = date (
            int (form_data ['year_end']),
            int (form_data ['month_end']),
            day
          )
          break
        except:
          day -= 1
      debets = Debets.objects.filter (date__gte = date_start, date__lte = date_end)
      o = {}
      c = {}
      t = {}
      akkum = 0
      for debet in debets:
        akkum += debet.summa
        if debet.payer not in o: o [debet.payer] = 0
        if debet.card  not in c: c [debet.card]  = 0
        if debet.type  not in t: t [debet.type]  = 0
        o [debet.payer] += debet.summa
        c [debet.card]  += debet.summa
        t [debet.type]  += debet.summa
      orgs  = []
      cards = []
      types = []
      for org  in o: orgs.append  ({'name': org.name,  'summa': o [org]})
      for card in c: cards.append ({'name': card.name, 'summa': c [card]})
      for type in t: types.append ({'name': type.name, 'summa': t [type]})
      context ['date_start'] = date_start
      context ['date_end']   = date_end
      context ['debets']     = debets
      context ['orgs']       = orgs
      context ['cards']      = cards
      context ['types']      = types
      context ['akkum']      = akkum
      return render (request, page, context)
    else:
      return case_period (request)
  else:
    return case_period (request)

def display_debet_types (request):
  '''Вывод типов дохода'''
  if not is_user (request): return redirect ('/')
  page    = 'budget/display_debet_types.html'
  context = default_context (request)
  context ['debet_types'] = DebetTypes.objects.all ()
  return render (request, page, context)

def display_org_types (request):
  '''Вывод типов организаций'''
  if not is_user (request): return redirect ('/')
  page    = 'budget/display_org_types.html'
  context = default_context (request)
  context ['org_types'] = OrgTypes.objects.all ()
  return render (request, page, context)

def index (request):
  '''Стартовое представление приложения'''
  if not is_user (request): return redirect ('/')
  page    = 'budget/index.html'
  context = default_context (request)
  context ['items'] = create_menu_app ('budget')
  return render (request, page, context)

