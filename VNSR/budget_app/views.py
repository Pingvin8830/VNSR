from django.shortcuts                   import render, redirect
from django.template.context_processors import csrf
from .forms                             import AddCardForm
from .models                            import Cards
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
    page = 'budget/add_card.html'
    context = default_context (request)
    context.update (csrf (request))
    context ['form'] = AddCardForm
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

def display_cards (request):
  '''Вывод мест хранения средств'''
  if not is_user (request): return redirect ('/')
  page    = 'budget/display_cards.html'
  context = default_context (request)
  context ['cards'] = Cards.objects.all ()
  return render (request, page, context)

def index (request):
  '''Стартовое представление приложения'''
  if not is_user (request): return redirect ('/')
  page    = 'budget/index.html'
  context = default_context (request)
  context ['items'] = create_menu_app ('budget')
  return render (request, page, context)
