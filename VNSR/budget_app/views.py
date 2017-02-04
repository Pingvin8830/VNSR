from django.shortcuts                   import render, redirect
from django.template.context_processors import csrf
from .forms                             import AddCardForm, AddDebetTypeForm, AddOrgTypeForm
from .models                            import Cards, DebetTypes, OrgTypes
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

def add_org_type (request):
  '''Добавляет новый тип организации'''
  if not is_user (request): return redirect ('/')
  if request.POST:
    form = AddOrgTypeForm (request.POST)
    if form.is_valid ():
      form.save ()
    return display_org_types (request)
  else:
    page    = 'budget/add_org_type.html'
    context = default_context (request)
    context.update (csrf (request))
    context ['form'] = AddOrgTypeForm
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

