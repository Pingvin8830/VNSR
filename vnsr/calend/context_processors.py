import calendar
from datetime import datetime
from calend import constants

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
  for day in calend.itermonthdays2(now.year, now.month):
    if day[1] == 0:
      calend_now += '<tr>'
    if day[0] == 0:
      calend_now += '<td></td>'
    else:
      calend_now += '<td>%d</td>' % day[0]
    if day[1] == 6:
      calend_now += '</tr>'
  calend_now += '</table>'
  return {'calend_now': calend_now}
