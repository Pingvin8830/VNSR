def decimal_to_money (count, decimal = 2, comment = '', delimiter = ','):
  '''Переводит значение DECIMAL из БД в печатный вид'''
  if count:
    count     = str (count).replace (',', '.')
  else:
    count = '0.00'
  fnd       = count.find ('.')
  length    = len (count)
  count_int = count [:fnd]
  count_dec = count [fnd + 1:fnd + decimal]
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
