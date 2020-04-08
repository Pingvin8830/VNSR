import calendar
from datetime import datetime, date
from django.contrib.auth.mixins import LoginRequiredMixin
from django.shortcuts import redirect
from django.urls import reverse_lazy
from django.views import generic

from calend import constants, models
# Create your views here.

class Index(LoginRequiredMixin, generic.RedirectView):
  login_url = reverse_lazy('auth_app:login')
  redirect_field_name = None
  http_method_names = ['get']
  permanent = True
  url = reverse_lazy('calend:year', args=[datetime.today().year])

class Month(LoginRequiredMixin, generic.TemplateView):
  login_url = reverse_lazy('auth_app:login')
  redirect_field_name = None
  http_method_names = ['get']
  template_name = 'calend/month.html'

  def get_context_data(self, *args, **kwargs):
    context = super().get_context_data(*args, **kwargs)
    context['year'] = {
      'prev': self.kwargs['year'],
      'now': self.kwargs['year'],
      'next': self.kwargs['year']
    }
    context['month'] = {
      'prev': self.kwargs['month']-1,
      'now': self.kwargs['month'],
      'name': constants.MONTHS_TEXT[self.kwargs['month']],
      'next': self.kwargs['month']+1
    }
    if not context['month']['prev']:
      context['year']['prev'] -= 1
      context['month']['prev'] = 12
    if context['month']['next'] == 13:
      context['year']['next'] += 1
      context['month']['next'] = 1
    context['days'] = []
    for i in calendar.Calendar().itermonthdates(self.kwargs['year'], self.kwargs['month']):
      if i.month == self.kwargs['month']:
        day = {'num': i.day, 'weekday': constants.DAYS_TEXT[i.weekday()], 'signs': [], 'marks': []}
        for mark in models.Marks.objects.filter(date=i):
          if mark.sign not in day['signs']: day['signs'].append(mark.sign)
          day['marks'].append(mark)
        try:
          date_type = models.Productions.objects.get(date=i).type
        except models.Productions.DoesNotExist:
          date_type = None
        if date_type == 'В': day['type'] = 'week'
        elif date_type == 'П': day['type'] = 'holiday'
        elif date_type == 'С': day['type'] = 'short'
        else: day['type'] = 'work'
        context['days'].append(day)
      pass
    return context

class SetProduction(LoginRequiredMixin, generic.TemplateView):
  login_url = reverse_lazy('auth_app:login')
  redirect_field_name = None
  http_method_names = ['get', 'post']
  template_name = 'calend/set_production.html'

  def get(self, request, *args, **kwargs):
    if not self.kwargs:
      return redirect(reverse_lazy('calend:set_production', args=(date.today().year, date.today().month)))
    response = super().get(request, *args, **kwargs)
    return response

  def get_context_data(self, *args, **kwargs):
    context = super().get_context_data(*args, **kwargs)
    context['year'] = { 
      'now': self.kwargs['year'],
      'prev': self.kwargs['year'],
      'next': self.kwargs['year']
    }
    context['month'] = {
      'now_text': constants.MONTHS_TEXT[self.kwargs['month']],
      'now': self.kwargs['month'],
      'prev': self.kwargs['month']-1,
      'next': self.kwargs['month']+1
    }
    if not context['month']['prev']:
      context['year']['prev'] -= 1
      context['month']['prev'] = 12
    if context['month']['next'] == 13:
      context['year']['next'] += 1
      context['month']['next'] = 1
    context['days'] = []
    for day in calendar.Calendar().itermonthdates(self.kwargs['year'], self.kwargs['month']):
      if day.month == self.kwargs['month']:
        day_context = { 'date': day.day, 'weekday': constants.DAYS_TEXT[day.weekday()] }
        try:
          day_context['type'] = models.Productions.objects.get(date=day).type
        except models.Productions.DoesNotExist:
          day_context['type'] = None
        context['days'].append(day_context)
    return context

  def post(self, request, *args, **kwargs):
    for day in request.POST:
      try:
        production = models.Productions.objects.get(
          date=date(
            int(request.POST['year']),
            int(request.POST['month']),
            int(day)
          )
        )
      except ValueError:
        continue
      except models.Productions.DoesNotExist:
        production = models.Productions(
          date=date(
            int(request.POST['year']),
            int(request.POST['month']),
            int(day)
          )
        )
      if request.POST[day] == 'Р' and production.id:
        production.delete()
      elif request.POST[day] != production.type and request.POST[day] != 'Р':
        production.type = request.POST[day]
        production.save()
    return redirect(reverse_lazy('calend:set_production', args=(request.POST['year'], request.POST['month'])))

class Year(LoginRequiredMixin, generic.TemplateView):
  login_url = reverse_lazy('auth_app:login')
  redirect_field_name = None
  http_method_names = ['get']
  template_name = 'calend/year.html'

  def get_context_data(self, *args, **kwargs):
    context = super().get_context_data(*args, **kwargs)
    context['year'] = {
      'prev': self.kwargs['year']-1,
      'now': self.kwargs['year'],
      'next': self.kwargs['year']+1
    }
    context['kvartals'] = []
    for i in range(1, 5):
      kvartal = {'num': i, 'months': []}
      for j in range(i*3-2, i*3+1):
        month = {
          'num': j,
          'name': constants.MONTHS_TEXT[j],
          'days': []
        }
        for k in calendar.Calendar().itermonthdates(self.kwargs['year'], j):
          day = {}
          if k.month == j:
            day['num'] = k.day
            day['weekday'] = k.weekday()
            try:
              date_type = models.Productions.objects.get(date=k).type
            except models.Productions.DoesNotExist:
              date_type = None
            if date_type == 'В':   day['type'] = 'week'
            elif date_type == 'П': day['type'] = 'holiday'
            elif date_type == 'С': day['type'] = 'short'
            else:                  day['type'] = 'work'
          else:
            day['num'] = ''
          month['days'].append(day)
        kvartal['months'].append(month)
      context['kvartals'].append(kvartal)
    return context

