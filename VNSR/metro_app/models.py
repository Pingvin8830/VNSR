from datetime             import datetime, date, time
from django.db            import models
from .functions           import decimal_to_money, control_period, time_to_int, int_to_time
from calend_app.functions import get_month_text, get_now
from calend_app.models    import Signs

# Create your models here.

class WorkPlane (models.Model):
  '''План смен'''
  class Meta ():
    db_table        = 'work_plane'
    unique_together = (('date_start', 'code'))

  id          = models.AutoField (primary_key = True)
  date_start  = models.DateField ()
  date_end    = models.DateField ()
  code        = models.CharField (max_length = 5)
  start       = models.TimeField ()
  end         = models.TimeField ()
  lanch       = models.BooleanField      (default = True)
  break_day   = models.SmallIntegerField (default = 2)
  break_night = models.SmallIntegerField (default = 0)

class ShedulePlane (models.Model):
  '''Планируемый график'''
  class Meta ():
    db_table = 'shedule_plane'

  id    = models.AutoField (primary_key = True)
  data  = models.DateField (unique      = True)
  shift = models.CharField (max_length  = 5)

class SheduleReal (models.Model):
  '''Реальный график'''
  class Meta ():
    db_table = 'shedule_real'

  def __str__ (self):
    return '%s' % self.data

  id          = models.AutoField         (primary_key = True      )
  data        = models.DateField         (unique      = True      )
  start       = models.TimeField         (                   null = True)
  end         = models.TimeField         (                   null = True)
  lanch       = models.BooleanField      (default = True)
  break_day   = models.SmallIntegerField (default = 2,       null = True)
  break_night = models.SmallIntegerField (default = 0,       null = True)
  delay       = models.TimeField         (default = '00:00', null = True)
  vacation    = models.BooleanField      (default = False)
  sick        = models.BooleanField      (default = False)

  def if_sick (self):
    if self.sick:
      self.start       = time (0, 0, 0)
      self.end         = time (0, 0, 0)
      self.break_day   = 0
      self.break_night = 0

  def hours (self):
    '''Считает количество рабочих часов'''
    if self.start > self.end: e = datetime.combine (date (1, 1, 2), self.end)
    else:                     e = datetime.combine (date (1, 1, 1), self.end)
    s = datetime.combine (date (1, 1, 1), self.start)
    self.hours = (e - s).seconds - self.break_day * 30 * 60 - self.break_night * 30 * 60
    self.hours = self.hours / 60 / 60

  def night (self):
    '''Считает количество ночных часов'''
    # 00 01 02 03 04 05 06|07 08 09 10 11 12 13 14 15 16 17 18 19 20 21|22 23 24||01 02 03 04 05 06|07 08 09 10 11 12 13 14 15 16 17 18 19 20 21|22 23 24|
    #                     |                                            |        ||                 |                                            |        |
    # 1        ------     |                                            |        ||                 |                                            |        |
    # 2        -----------+------------------------------------------  |        ||                 |                                            |        |
    # 3        -----------+--------------------------------------------+---     ||                 |                                            |        |
    # 4        -----------+--------------------------------------------+--------++---              |                                            |        |

    # 5                   |               ---------------------------  |        ||                 |                                            |        |
    # 6                   |               -----------------------------+---     ||                 |                                            |        |
    # 7                   |               -----------------------------+--------++------------     |                                            |        |
    # 8                   |               -----------------------------+--------++-----------------+------------                                |        |

    # 9                   |                                            |   ---  ||                 |                                            |        |
    # 10                  |                                            |   -----++------------     |                                            |        |
    # 11                  |                                            |   -----++-----------------+------------------------------------------  |        |
    # 12                  |                                            |   -----++-----------------+--------------------------------------------+---     |
    #                     1                                            2        3                  4                                            5        6

    if self.start > self.end: end = datetime.combine (date (1, 1, 2), self.end)
    else:                     end = datetime.combine (date (1, 1, 1), self.end)
    start = datetime.combine (date (1, 1, 1), self.start)

    ctrl_fst_mng = datetime (1, 1, 1, 6)
    ctrl_fst_evn = datetime (1, 1, 1, 22)
    ctrl_fst_day = datetime (1, 1, 2)
    ctrl_scn_mng = datetime (1, 1, 2, 6)
    ctrl_scn_evn = datetime (1, 1, 2, 22)
    ctrl_scn_day = datetime (1, 1, 3)

    if   (start < ctrl_fst_mng) and (end <= ctrl_fst_mng): self.night = (end          - start       ).seconds / 3600      - self.break_night * 0.5
    elif (start < ctrl_fst_mng) and (end <= ctrl_fst_evn): self.night = (ctrl_fst_mng - start       ).seconds / 3600      - self.break_night * 0.5
    elif (start < ctrl_fst_mng) and (end <= ctrl_fst_day): self.night = (end          - start       ).seconds / 3600 - 16 - self.break_night * 0.5
    elif (start < ctrl_fst_mng) and (end <= ctrl_scn_mng): self.night = (end          - start       ).seconds / 3600 - 16 - self.break_night * 0.5
    elif (start < ctrl_fst_evn) and (end <= ctrl_fst_evn): self.night = 0
    elif (start < ctrl_fst_evn) and (end <= ctrl_fst_day): self.night = (end          - ctrl_fst_evn).seconds / 3600      - self.break_night * 0.5
    elif (start < ctrl_fst_evn) and (end <= ctrl_scn_mng): self.night = (end          - ctrl_fst_evn).seconds / 3600      - self.break_night * 0.5
    elif (start < ctrl_fst_evn) and (end <= ctrl_scn_evn): self.night = 8                                                 - self.break_night * 0.5
    elif (start < ctrl_fst_day) and (end <= ctrl_fst_day): self.night = (end          - start       ).seconds / 3600      - self.break_night * 0.5
    elif (start < ctrl_fst_day) and (end <= ctrl_scn_mng): self.night = (end          - start       ).seconds / 3600      - self.break_night * 0.5
    elif (start < ctrl_fst_day) and (end <= ctrl_scn_evn): self.night = (ctrl_scn_mng - start       ).seconds / 3600      - self.break_night * 0.5
    elif (start < ctrl_fst_day) and (end <= ctrl_scn_day): self.night = (end          - start       ).seconds / 3600 - 16 - self.break_night * 0.5

  def holiday (self):
    signs = Signs.objects.get (data = self.data)
    if signs.holiday:
      if self.start > self.end:
        self.week  = (datetime (1, 1, 2) - datetime.combine (date (1, 1, 1), self.start)).seconds / 3600 - self.break_day * 0.5 - self.break_night * 0.5
        self.night = 0
      else:
        self.week  = self.hours
        self.night = 0
    else:
      self.week = 0

class Lanch (models.Model):
  '''Стоимость обеда'''
  class Meta ():
    db_table        = 'lanch'
    unique_together = (('start', 'end'))

  id    = models.AutoField    (primary_key = True)
  start = models.DateField    ()
  end   = models.DateField    (null        = True)
  value = models.DecimalField (max_digits  = 9, decimal_places = 4)

class Rate (models.Model):
  '''Почасовая ставка'''
  class Meta ():
    db_table        = 'rate'
    unique_together = (('start', 'end'))

  id    = models.AutoField    (primary_key = True)
  start = models.DateField    ()
  end   = models.DateField    (null        = True)
  value = models.DecimalField (max_digits  = 9, decimal_places = 4)

class CodesPayslip (models.Model):
  '''Коды НДФЛ'''
  INCOME      = 'i'
  CONSUMPTION = 'c'
  OTHERS      = 'o'
  TYPES = (
    (INCOME,      'Income'),
    (CONSUMPTION, 'Consumption'),
    (OTHERS,      'Others')
  )

  class Meta ():
    db_table = 'codes_payslip'

  id   = models.AutoField (primary_key = True)
  code = models.CharField (max_length  = 8, unique = True)
  name = models.CharField (max_length  = 50)
  type = models.CharField (max_length  = 1, choices = TYPES)

class Payslip (models.Model):
  '''Расчетный листок'''
  class Meta ():
    db_table = 'payslip'

  id          = models.AutoField    (primary_key = True)
  period      = models.DateField    (unique      = True)
  division    = models.CharField    (default = 'Оптовая продажа алкоголя', max_length  = 50)
  post        = models.CharField    (default = 'Кассир-оператор ПК',       max_length  = 50)
  rate        = models.DecimalField (             max_digits = 9, decimal_places = 4)
  begin_dolg  = models.DecimalField (default = 0, max_digits = 9, decimal_places = 4)
  rotate_dolg = models.DecimalField (null = True, max_digits = 9, decimal_places = 4)
  end_dolg    = models.DecimalField (default = 0, max_digits = 9, decimal_places = 4)
  income      = models.DecimalField (             max_digits = 9, decimal_places = 4)
  consumption = models.DecimalField (             max_digits = 9, decimal_places = 4)
  paying      = models.DecimalField (             max_digits = 9, decimal_places = 4)

  def print_edit (self):
    '''Приводит данные из БД к печатному виду'''
    self.rate        = decimal_to_money (self.rate)
    self.begin_dolg  = decimal_to_money (self.begin_dolg)
    self.rotate_dolg = decimal_to_money (self.rotate_dolg)
    self.end_dolg    = decimal_to_money (self.end_dolg)
    self.income      = decimal_to_money (self.income)
    self.consumption = decimal_to_money (self.consumption)
    self.paying      = decimal_to_money (self.paying)
    self.text_period = get_month_text (self.period.year, self.period.month)
    self.period      = str (self.period.month) + '/' + str (self.period.year) [2:4]
    if len (self.period) == 4: self.period = '0' + self.period

  def differences (self):
    '''Проверка соответствия данных'''
    res = {
      'rate': self.dif_rate (),
    }
    return res

  def differences_details (self):
    '''Проверка соответствия данных детализаций'''
    res = {}
    details = PayslipDetails.objects.filter (payslip = self.id).order_by ('code').order_by ('period')
    for detail in details:
      res [detail] = detail.differences ()
    return res

  def dif_rate (self):
    '''Проверка расхождения почасовой ставки'''
    res = []
    control_rate = Rate.objects.filter (start__lte = self.period).get (end__gte = self.period).value
    if control_rate != self.rate: res.append (('Несоответствие почасовой ставки', decimal_to_money (control_rate), decimal_to_money (self.rate), decimal_to_money (self.rate - control_rate)))
    return res

  def control (self):
    '''Проверка расчетного листка'''
    return {
      'rate':        self.control_rate (),
      'dolg':        self.control_dolg (),
      'income':      self.control_income (),
      'consumption': self.control_consumption (),
      'paying':      self.control_paying ()
    }

  def control_details (self):
    '''Проверка детализаций'''
    res = {}
    details = PayslipDetails.objects.filter (payslip = self.id).order_by ('code').order_by ('period')
    for detail in details:
      res [detail] = detail.control ()
    return res

  def control_paying (self):
    '''Проверка перечисления в банк'''
    res = []
    if self.paying < 0: res.append ('Отрицательное перечисление')
    if self.paying != self.income - self.consumption: res.append ('Несоответствие суммы перечисления')
    return res

  def control_consumption (self):
    '''Проверка удержания'''
    res = []
    if self.consumption < 0: res.append ('Отрицательное удержание')
    control_summa = 0
    for detail in PayslipDetails.objects.filter (payslip = self).filter (code__type = 'c'): control_summa += detail.summa
    if control_summa != self.consumption: res.append ('Несоответствие суммы удержания')
    return res

  def control_income (self):
    '''Проверка начисления'''
    res = []
    if self.income < 0: res.append ('Отрицательное начисление')
    control_summa = 0
    for detail in PayslipDetails.objects.filter (payslip = self).filter (code__type = 'i'): control_summa += detail.summa
    if control_summa != self.income: res.append ('Несоответствие суммы начисления')
    return res

  def control_dolg (self):
    '''Проверка долгов работника'''
    res = []
    if self.begin_dolg - self.rotate_dolg != self.end_dolg: res.append ('Несоответствие Begin - Rotate = End')
    return res

  def control_rate (self):
    '''Проверка оклада/тарифа (почасовой ставки)'''
    res = []
    if self.rate <= 0: res.append ('Ставка должна быть положительной')
    return res

class PayslipDetails (models.Model):
  '''Детализация расчетного листка'''
  class Meta ():
    db_table        = 'payslip_details'
    unique_together = (('payslip', 'code', 'period'))

  id      = models.AutoField    (primary_key = True)
  payslip = models.ForeignKey   ('Payslip',      null = True, on_delete = models.SET_NULL, db_column = 'payslip')
  code    = models.ForeignKey   ('CodesPayslip', null = True, on_delete = models.SET_NULL, db_column = 'code')
  period  = models.DateField    ()
  summa   = models.DecimalField (null = True, max_digits = 9, decimal_places = 4)
  count   = models.DecimalField (null = True, max_digits = 9, decimal_places = 4)

  def print_edit (self):
    '''Приводит данные из БД к печатному виду'''
    self.type       = self.code.type
    self.code_print = self.code.code + ' ' + self.code.name
    self.period     = str (self.period.month) + '/' + str (self.period.year) [2:4]
    if len (self.period) == 4: self.period = '0' + self.period
    self.summa      = decimal_to_money (self.summa)
    self.count      = decimal_to_money (self.count)

  def control (self):
    '''Проверка детализации'''
    res = ['%s' % (self.code.code)]
    res = ['%s %s %s %s %s' % (self.code.code, self.code.name, self.period, self.summa, self.count)]
    code = self.code.code
    if   code == '6101': res = self.control_6101 ()
    elif code == '6200': res = self.control_6200 ()
    elif code == '/322': res = self.control_0322 ()
    elif code == '/853': res = self.control_0853 ()
    elif code == '1329': res = self.control_1329 ()
    elif code == '2720': res = self.control_2720 ()
    elif code == '3007': res = self.control_3007 ()
    elif code == '3009': res = self.control_3009 ()
    return res

  def differences (self):
    '''Проверка соответствия данных'''
    res = ['%s' % (self.code.code)]
    res = ['%s %s %s' % (self.period, self.summa, self.count)]
    code = self.code.code
    if   code == '/853': res = self.diff_0853 ()
    elif code == '1329': res = self.diff_1329 ()
    return res

  def diff_1329 (self):
    '''Проверка расхождения почасовой оплаты'''
    res = []
    control_count = 0
    control_summa = 0
    control_period = str (self.period) [:7]
    control_rate   = float (Rate.objects.filter (start__lte = self.period).get (end__gte = self.period).value)
    if self.period != self.payslip.period:
      shifts = SheduleReal.objects.filter (data__gte = '%s-16' % control_period).filter (data__lte = '%s-31' % control_period)
    else:
      shifts = SheduleReal.objects.filter (data__gte = '%s-01' % control_period).filter (data__lte = '%s-15' % control_period)
    for shift in shifts:
      start = shift.start.hour + shift.start.minute / 60.0 + shift.start.second / 60.0 / 60.0
      end   = shift.end.hour   + shift.end.minute   / 60.0 + shift.end.second   / 60.0 / 60.0
      brk   = (shift.break_day + shift.break_night) / 2.0
      control_count += end - start - brk
    control_summa = control_rate * control_count
    if control_count != self.count: res.append (('Несоответствие количества', decimal_to_money (control_count), decimal_to_money (self.count), decimal_to_money (float (self.count) - control_count)))
    if control_summa != self.summa: res.append (('Несоответствие суммы',      decimal_to_money (control_summa), decimal_to_money (self.summa), decimal_to_money (float (self.summa) - control_summa)))
    return res

  def diff_0853 (self):
    '''Проверка расхождения факт-дней'''
    res = []
    control_count = 0
    control_period = str (self.period) [:7]
    if self.period != self.payslip.period:
      shifts = SheduleReal.objects.filter (data__gte = '%s-16' % control_period).filter (data__lte = '%s-31' % control_period)
    else:
      shifts = SheduleReal.objects.filter (data__gte = '%s-01' % control_period).filter (data__lte = '%s-15' % control_period)
    for shift in shifts:
      start = shift.start.hour + shift.start.minute / 60.0 + shift.start.second / 60.0 / 60.0 
      end   = shift.end.hour   + shift.end.minute   / 60.0 + shift.end.second   / 60.0 / 60.0
      brk   = (shift.break_day + shift.break_night) / 2.0
      control_count += end - start - brk
    control_count = control_count / 8.0
    if control_count != self.count: res.append (('Несоответствие количества', decimal_to_money (control_count), decimal_to_money (self.count), decimal_to_money (float (self.count) - control_count)))
    return res

  def control_3009 (self):
    '''Проверка праздничных'''
    res = []
    if not control_period (self.payslip.period, self.period): res.append ('Несоответствие периода')
    if self.summa <= 0: res.append ('Сумма должна быть положительной')
    if self.summa != self.count * self.payslip.rate: res.append ('Несоответствие суммы')
    if self.count <= 0: res.append ('Количество должно быть положительным')
    return res

  def control_3007 (self):
    '''Проверка ночных'''
    res = []
    if not control_period (self.payslip.period, self.period): res.append ('Несоответствие периода')
    if self.summa <=0 : res.append ('Сумма должна быть положительной')
    if float (self.summa) != float (self.count * self.payslip.rate) * 0.4: res.append ('Несоответствие суммы')
    if self.count <= 0: res.append ('Количество должно быть положительным')
    return res

  def control_2720 (self):
    '''Проверка доп дохода питание'''
    res = []
    if not control_period (self.payslip.period, self.period): res.append ('Несоответствие периода')
    if self.summa < 0: res.append ('Отрицательная сумма')
    if self.count:     res.append ('Присутствие количества')
    return res

  def control_1329 (self):
    '''Проверка почасовой оплаты'''
    res = []
    if not control_period (self.payslip.period, self.period): res.append ('Несоответствие периода')
    if self.summa < 0: res.append ('Отрицательная сумма')
    if self.count < 0: res.append ('Отрицательное количество')
    if self.summa != self.count * self.payslip.rate: res.append ('Несоответствие суммы')
    return res

  def control_0853 (self):
    '''Проверка фактических дней'''
    res = []
    if not control_period (self.payslip.period, self.period): res.append ('Несоответствие периода')
    if self.summa:     res.append ('Присутствие суммы')
    if self.count < 0: res.append ('Отрицательное количество')
    control_count = PayslipDetails.objects.filter (payslip = self.payslip).filter (code__code = '1329').get (period = self.period)
    control_count = float (control_count.count)
    if float (self.count) - (control_count / 8): res.append ('Несоответствие количества')
    return res

  def control_0322 (self):
    '''Проверка подоходного налога'''
    res = []
    if not control_period (self.payslip.period, self.period, True): res.append ('Несоответствие периода')
    if self.summa < 0: res.append ('Отрицательная сумма')
    control_summa = 0
    for detail in PayslipDetails.objects.filter (payslip = self.payslip).exclude (code__type = 'c'):
      control_summa += float (detail.summa)
    control_summa *= 0.13
    if self.summa != control_summa: res.append ('Неверная сумма')
    if self.count:     res.append ('Присутствие количества')
    return res

  def control_6200 (self):
    '''Проверка удержания за питание'''
    res = []
    if not control_period (self.payslip.period, self.period): res.append ('Несоответствие периода')
    if self.summa < 0: res.append ('Отрицательная сумма')
    if self.count:     res.append ('Присутствие количества')
    return res

  def control_6101 (self):
    '''Проверка аванса'''
    res = []
    if not control_period (self.payslip.period, self.period, True): res.append ('Несоответствие периода')
    if self.summa < 0: res.append ('Отрицательная сумма')
    if self.count:     res.append ('Присутствие количества')
    return res

