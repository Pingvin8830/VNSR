import calendar
from datetime import datetime
from django.contrib.auth.mixins import LoginRequiredMixin
from django.urls import reverse_lazy
from django.views import generic

from calend import constants
# Create your views here.

class Index(LoginRequiredMixin, generic.RedirectView):
  login_url = reverse_lazy('auth_app:login')
  redirect_field_name = None
  http_method_names = ['get']
  permanent = True
  url = reverse_lazy('calend:year', args=[datetime.today().year])

class Year(LoginRequiredMixin, generic.TemplateView):
  login_url = reverse_lazy('auth_app:login')
  redirect_field_name = None
  http_method_names = ['get']
  template_name = 'calend/year.html'

  def get_context_data(self, *args, **kwargs):
    context = super().get_context_data(*args, **kwargs)
    calend = '<table>'
    calend += '<caption>'
    calend += '<a href="%s"><--</a>' % reverse_lazy('calend:year', args=[self.kwargs['year']-1])
    calend += '%d' % self.kwargs['year']
    calend += '<a href="%s">--></a>' % reverse_lazy('calend:year', args=[self.kwargs['year']+1])
    calend += '</caption>'
    for month in range(1, 13):
      if month % 4 == 1:
        calend += '<tr>'
      calend += '<td>'
      calend += self.get_month_html(self.kwargs['year'], month)
      calend += '</td>'
      if not month % 4:
        calend += '</tr>'
    calend += '</table>'
    context['calend'] = calend
    return context

  def get_month_html(self, year, month):
    calend = calendar.Calendar()
    res = '<table>'
    res += '<caption>%s</caption>' % constants.MONTHS_TEXT[month]
    res += '<tr>'
    res += '<th>Пн</th>'
    res += '<th>Вт</th>'
    res += '<th>Ср</th>'
    res += '<th>Чт</th>'
    res += '<th>Пт</th>'
    res += '<th class="week">Сб</th>'
    res += '<th class="week">Вс</th>'
    res += '</tr>'
    for day in calend.itermonthdays2(year, month):
      if day[1] == 0:
        res += '<tr>'
      if day[0]:
        res += '<td>%d</td>' % day[0]
      else:
        res += '<td></td>'
      if day[1] == 6:
        res += '</tr>'
    res += '</table>'
    return res
