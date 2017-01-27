def decimal_to_money (count, decimal = 2, comment = '', delimiter = ','):
  '''Переводит значение DECIMAL из БД в печатный вид'''
  if count:
    count     = str (count).replace (',', '.')
  else:
    count = '0.00'
  fnd       = count.find ('.')
  length    = len (count)
  count_int = count [:fnd]
  fnd += 1
  count_dec = count [fnd:fnd + decimal]
  count_new = ''
  length    = len (count_int)
  num       = 0
  for i in range (length):
    if num == 3:
      count_new = delimiter + count_new
      count_new = count [length - i - 1] + count_new
      num = 0
    else:
      count_new = count [length - 1 - i] + count_new
      num += 1
  count_new += '.'
  for i in range (decimal):
    try:
      count_new += count_dec [i]
    except:
      count_new += '0'
  count_new += comment
  return count_new

def control_period (control_period, test_period, uniq = False):
  '''Проверка периода в детализации расчетного листка'''
  res = True
  control_date = control_period.year * 12 + control_period.month
  test_date    = test_period.year    * 12 + test_period.month
  if uniq:
    if control_date != test_date: res = False
  else:
    if (control_date - test_date > 1) or (control_date - test_date < 0): res = False
  return res

