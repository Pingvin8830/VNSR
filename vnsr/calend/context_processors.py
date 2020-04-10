from calendar import Calendar
from datetime import datetime
from calend import constants, models

def calend(request):
  now = datetime.today()
  calend_now = {
    'month_text': constants.MONTHS_TEXT[now.month],
    'month': now.month,
    'year': now.year,
    'days': []
  }
  for day in Calendar().itermonthdates(now.year, now.month):
    add = {
      'day':  day.day,
      'month': day.month,
      'weekday': day.weekday(),
      'mark': ''
    }
    try:
      add['type'] = models.Productions.objects.get(date=day).type
    except models.Productions.DoesNotExist:
      add['type'] = ''
    if add['type'] == 'В':
      add['type'] = 'week'
    elif add['type'] == 'П':
      add['type'] = 'holiday'
    elif add['type'] == 'С':
      add['type'] = 'short'
    if models.Marks.objects.filter(date=day).count():
      add['mark'] = 'mark'
    calend_now['days'].append(add)

  return {'calend_now': calend_now}
