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
        day_context = { 'date': day.day }
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
    calend = '<table>\n'
    calend += '<caption>\n'
    calend += '<a href="%s"><--</a>\n' % reverse_lazy('calend:year', args=[self.kwargs['year']-1])
    calend += '%d\n' % self.kwargs['year']
    calend += '<a href="%s">--></a>\n' % reverse_lazy('calend:year', args=[self.kwargs['year']+1])
    calend += '</caption>\n'
    kvartal = 0
    for month in range(1, 13):
      if month % 3 == 1:
        kvartal += 1
        calend += '<tr>\n'
        calend += '<td>%d квартал</td>\n' % kvartal
      calend += '<td>\n'
      calend += self.get_month_html(self.kwargs['year'], month)
      calend += '</td>\n'
      if not month % 3:
        calend += '</tr>\n'
    calend += '</table>\n'
    context['calend'] = calend
    return context

  def get_month_html(self, year, month):
    calend = calendar.Calendar()
    res = '<table>\n'
    res += '<caption>%s</caption>\n' % constants.MONTHS_TEXT[month]
    res += '<tr> '
    res += '<th>Пн</th> '
    res += '<th>Вт</th> '
    res += '<th>Ср</th> '
    res += '<th>Чт</th> '
    res += '<th>Пт</th> '
    res += '<th class="week">Сб</th> '
    res += '<th class="week">Вс</th> '
    res += '</tr>\n'
    for day in calend.itermonthdays2(year, month):
      if day[0]:
        try:
          date_type = models.Productions.objects.get(date=date(year, month, day[0])).type
        except models.Productions.DoesNotExist:
          date_type = None
      if day[1] == 0:
        res += '<tr> '
      if day[0]:
        if date_type == 'В':
          res += '<td class="week right_text">%d</td> ' % day[0]
        elif date_type == 'П':
          res += '<td class="holiday right_text">%d</td> ' % day[0]
        elif date_type == 'С':
          res += '<td class="short right_text">%d</td> ' % day[0]
        else:
          res += '<td class="right_text">%d</td> ' % day[0]
      else:
        res += '<td>  </td> '
      if day[1] == 6:
        res += '</tr>\n'
    res += '</table>\n'
    return res
