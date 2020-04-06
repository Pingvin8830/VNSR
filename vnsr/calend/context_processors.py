import calendar
from datetime import datetime
from calend import constants, models

def calend(request):
  now = datetime.today()
  calend = calendar.Calendar()
  calend_now =  '<table>'
  calend_now += '<caption>%s %d</caption>' % (constants.MONTHS_TEXT[now.month], now.year) 
  calend_now += '<tr>'
  calend_now += '<th>Пн</th>'
  calend_now += '<th>Вт</th>'
  calend_now += '<th>Ср</th>'
  calend_now += '<th>Чт</th>'
  calend_now += '<th>Пт</th>'
  calend_now += '<th class="week">Сб</th>'
  calend_now += '<th class="week">Вс</th>'
  calend_now += '</tr>'
  for day in calend.itermonthdates(now.year, now.month):
    try:
      production = models.Productions.objects.get(date=day)
    except models.Productions.DoesNotExist:
      production = models.Productions(date=day)
    if day.weekday() == 0:
      calend_now += '<tr>'
    if day.month != now.month:
      calend_now += '<td></td>'
    else:
      if not production.type:
        calend_now += '<td>%d</td>' % day.day
      elif production.type == 'В':
        calend_now += '<td class="week">%d</td>' % day.day
      elif production.type == 'П':
        calend_now += '<td class="holiday">%d</td>' % day.day
      elif production.type == 'С':
        calend_now += '<td class="short">%d</td>' % day.day
    if day.weekday() == 6:
      calend_now += '</tr>'
  calend_now += '</table>'
  return {'calend_now': calend_now}
