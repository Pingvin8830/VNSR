from datetime import date, timedelta
from .models  import Signs

def get_now ():
  '''Возвращает значение текущей даты'''
  return date (1, 1, 1).today ()

def get_month_text (year = get_now ().year, month = get_now ().month):
  '''Возвращает текстовое значение месяца по числовому'''
  month = int (month)
  MONTH = (
    'Январь',
    'Февраль',
    'Март',
    'Апрель',
    'Май',
    'Июнь',
    'Июль',
    'Август',
    'Сентябрь',
    'Октябрь',
    'Ноябрь',
    'Декабрь',
  )
  month_text  = MONTH [month - 1]
  month_text += ' %s г.' % year
  return month_text

def create_cell_calend (data):
  '''Формирует ячейку календаря'''
  try:
    signs = Signs.objects.get (data = data)
  except:
    signs = Signs (data = data)
  cell  = '<td class="%s">' % signs.get_class ()
  cell += str (data.day)
  cell += '</td>'
  return cell

def create_calend_month (year = get_now ().year, month = get_now ().month):
  '''Составляет календарь на месяц'''
  year   = int (year)
  month  = int (month)
  data   = date (year, month, 1)
  entry  = data.weekday ()
  calend = '<tr>'
  for i in range (entry):
    calend += '<td></td>'
  while data.weekday () != 0:
    calend += create_cell_calend (data)
    data   += timedelta (days = 1)
  calend += '</tr>'
  while data.month == month:
    calend += '<tr>' + create_cell_calend (data)
    data   += timedelta (days = 1)
    while data.weekday () != 0:
      if data.month == month:
        calend += create_cell_calend (data)
      else:
        calend += '<td></td>'
      data += timedelta (days = 1)
    calend += '</tr>'
  return calend

def create_line_radio (year, month, start, end, type_line):
  '''Создаёт линию радиокнопок'''
  line = '<tr><td>'
  if   type_line == 'work':    line += 'Рабочий</td>'
  elif type_line == 'week':    line += 'Выходной</td>'
  elif type_line == 'holiday': line += 'Праздничный</td>'
  elif type_line == 'short':   line += 'Сокращённый</td>'
  for day in range (start, end):
    data = date (year, month, day)
    line += '<td><input type="radio" name="%s" value="%s"' % (str (data), type_line)
    try:
      signs = Signs.objects.get (data = data)
    except:
      signs = Signs (data = data, work = True)
    if signs.get_class () == type_line:
      line += 'checked'
    line += '></td>'
  line += '</tr>'
  return line

def create_month_signs_form (year, month, part):
  '''Создаёт одну часть формы настройки дней месяца'''
  form = '<tr><td></td>'
  if part == 1:
    start = 1
    end   = 17
  elif part == 2:
    start = 17
    end   = 32
    while True:
      try:
        control = date (year, month, end - 1)
        break
      except:
        end -= 1
    form += '</tr><tr height="20"></tr><tr><td></td>'
  for day in range (start, end):
    try:
      name = date (year, month, day)
    except:
      break
    form += '<td>%s</td>' % str (day)
  form += create_line_radio (year, month, start, end, 'work')
  form += create_line_radio (year, month, start, end, 'week')
  form += create_line_radio (year, month, start, end, 'holiday')
  form += create_line_radio (year, month, start, end, 'short')
  return form

