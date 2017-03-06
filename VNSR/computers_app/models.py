from django.db import models

# Create your models here.

class CPUs (models.Model):
  '''Процессоры'''
  class Meta (object):
    db_table = 'cpus'

  id                  = models.AutoField                 (primary_key = True,                              verbose_name = 'Код')
  name                = models.CharField                 (max_length = 30,                                 verbose_name = 'Название')
  frequency_count     = models.PositiveSmallIntegerField (                                                 verbose_name = 'Частота процессора')
  frequency_text      = models.CharField                 (max_length = 5,                                  verbose_name = 'Единица измерения частоты процессора')
  frequency_bus_count = models.PositiveSmallIntegerField (                                    null = True, verbose_name = 'Частота системной шины')
  frequency_bus_text  = models.CharField                 (max_length = 5,                     null = True, verbose_name = 'Единица измерения частоты системной шины')
  bit                 = models.PositiveSmallIntegerField (                                                 verbose_name = 'Разрядность')
  count               = models.PositiveSmallIntegerField (                                                 verbose_name = 'Количество ядер')
  tech                = models.CharField                 (max_length = 10,                    null = True, verbose_name = 'Техпроцесс')
  socket              = models.CharField                 (max_length = 10,                                 verbose_name = 'Сокет')
  cache_count         = models.PositiveSmallIntegerField (                                    null = True, verbose_name = 'Размер кэша')
  cache_text          = models.CharField                 (max_length = 5,                     null = True, verbose_name = 'Единица измерения объёма кэша')
  temp_max            = models.DecimalField              (max_digits = 5, decimal_places = 2, null = True, verbose_name = 'Максимальная рабочая температура')
  comment             = models.CharField                 (max_length = 100,                   null = True, verbose_name = 'Комментарий')

class HDDs (models.Model):
  '''Винчестеры'''
  class Meta (object):
    db_table = 'hdds'

  id           = models.AutoField                 (primary_key = True,            verbose_name = 'Код')
  maker        = models.CharField                 (max_length = 20,               verbose_name = 'Изготовитель')
  volume_count = models.PositiveSmallIntegerField (                               verbose_name = 'Объём')
  volume_text  = models.CharField                 (max_length = 5,                verbose_name = 'Единица измерения объёма')
  interface    = models.CharField                 (max_length = 10,               verbose_name = 'Интерфейс')
  time_connect = models.PositiveSmallIntegerField (                  null = True, verbose_name = 'Время доступа')
  time_read    = models.PositiveSmallIntegerField (                  null = True, verbose_name = 'Время чтения/записи')
  time_text    = models.CharField                 (max_length = 5,   null = True, verbose_name = 'Единица измерения времени')
  comment      = models.CharField                 (max_length = 100, null = True, verbose_name = 'Комментарий')

class Mothers (models.Model):
  '''Материнские платы'''
  class Meta (object):
    db_table = 'mothers'

  id            = models.AutoField                 (primary_key = True,            verbose_name = 'Код')
  name          = models.CharField                 (max_length = 30,               verbose_name = 'Название')
  chipset       = models.CharField                 (max_length = 20,               verbose_name = 'Чипсет')
  maker         = models.CharField                 (max_length = 20,               verbose_name = 'Производитель')
  factor        = models.CharField                 (max_length = 10,               verbose_name = 'Форм-фактор')
  socket        = models.CharField                 (max_length = 10,               verbose_name = 'Сокет')
  frequency_bus = models.CharField                 (max_length = 20,  null = True, verbose_name = 'Частота системной шины')
  ram_count     = models.PositiveSmallIntegerField (                               verbose_name = 'Количество слотов ОП')
  ram_type      = models.CharField                 (max_length = 20,               verbose_name = 'Тип ОП')
  is_dual       = models.BooleanField              (                               verbose_name = 'Признак поддержки двухканального режима ОП')
  pcie          = models.PositiveSmallIntegerField (                               verbose_name = 'Количество слотов PCI-Express')
  pci           = models.PositiveSmallIntegerField (                               verbose_name = 'Количество слотов PCI')
  ps            = models.PositiveSmallIntegerField (                  null = True, verbose_name = 'Количество выходов PS/2')
  usb_count     = models.PositiveSmallIntegerField (                               verbose_name = 'Количество выходов USB')
  usb_type      = models.CharField                 (max_length = 20,  null = True, verbose_name = 'Тип USB')
  ide           = models.PositiveSmallIntegerField (                  null = True, verbose_name = 'Количество слотов IDE')
  sata          = models.PositiveSmallIntegerField (                  null = True, verbose_name = 'Количество слотов SATA')
  is_video      = models.BooleanField              (                               verbose_name = 'Признак наличия видеокарты')
  comment       = models.CharField                 (max_length = 100, null = True, verbose_name = 'Комментарий')

class Networks (models.Model):
  '''Сетевые карты'''
  class Meta (object):
    db_table = 'networks'

  id              = models.AutoField                 (primary_key = True,            verbose_name =  'Код')
  name            = models.CharField                 (max_length = 30,  null = True, verbose_name = 'Название')
  bit             = models.PositiveSmallIntegerField (                  null = True, verbose_name = 'Разрядность')
  out_socket      = models.CharField                 (max_length = 10,               verbose_name = 'Выходной разъём')
  frequency_count = models.PositiveSmallIntegerField (                  null = True, verbose_name = 'Частота')
  frequency_text  = models.CharField                 (max_length = 10,  null = True, verbose_name = 'Единица измерения частоты')
  mac             = models.CharField                 (max_length = 17,               verbose_name = 'МАС-адрес')
  is_boot         = models.BooleanField              (                               verbose_name = 'Признак возможности загрузки по сети')
  is_wol          = models.BooleanField              (                               verbose_name = 'Признак возможности включения по сети')
  comment         = models.CharField                 (max_length = 100, null = True, verbose_name = 'Комментарий')

class RAMs (models.Model):
  '''Оперативная память'''
  class Meta (object):
    db_table = 'rams'

  id              = models.AutoField                 (primary_key = True,                              verbose_name = 'Код')
  name            = models.CharField                 (max_length = 30,  null = True,                   verbose_name = 'Название')
  type            = models.CharField                 (max_length = 20,                                 verbose_name = 'Тип')
  volume_count    = models.PositiveSmallIntegerField (                                                 verbose_name = 'Объём памяти')
  volume_text     = models.CharField                 (max_length = 5,                                  verbose_name = 'Единица измерения объёма')
  bit             = models.PositiveSmallIntegerField (                                    null = True, verbose_name = 'Разрядность')
  frequency_count = models.PositiveSmallIntegerField (                                    null = True, verbose_name = 'Частота')
  frequency_text  = models.CharField                 (max_length = 5,                     null = True, verbose_name = 'Единица измерения частоты')
  time            = models.CharField                 (max_length = 100,                   null = True, verbose_name = 'Тайминги')
  voltage         = models.DecimalField              (max_digits = 5, decimal_places = 2, null = True, verbose_name = 'Напряжение питания')
  comment         = models.CharField                 (max_length = 100,                   null = True, verbose_name = 'Комментарий')

class Videos (models.Model):
  '''Видеокарты'''
  class Meta (object):
    db_table = 'videos'

  id                     = models.AutoField                 (primary_key = True,            verbose_name = 'Код')
  name                   = models.CharField                 (max_length = 30,               verbose_name = 'Название')
  cpu_name               = models.CharField                 (max_length = 20,               verbose_name = 'Название процессора')
  cpu_frequency_count    = models.PositiveSmallIntegerField (                  null = True, verbose_name = 'Частота процессора')
  cpu_frequency_text     = models.CharField                 (max_length = 10,  null = True, verbose_name = 'Единица измерения частоты процессора')
  memory_volume_count    = models.PositiveSmallIntegerField (                               verbose_name = 'Объём памяти')
  memory_volume_text     = models.CharField                 (max_length = 10,               verbose_name = 'Единица измерения объёма памяти')
  memory_type            = models.CharField                 (max_length = 10,  null = True, verbose_name = 'Тип памяти')
  memory_frequency_count = models.PositiveSmallIntegerField (                  null = True, verbose_name = 'Частота памяти')
  memory_frequency_text  = models.CharField                 (max_length = 5,   null = True, verbose_name = 'Единица измерения частоты памяти')
  memory_bit             = models.PositiveSmallIntegerField (                  null = True, verbose_name = 'Разрядность памяти')
  tech                   = models.CharField                 (max_length = 10,  null = True, verbose_name = 'Техпроцесс')
  vga                    = models.PositiveSmallIntegerField (                               verbose_name = 'Количество выходов VGA')
  dvi                    = models.PositiveSmallIntegerField (                               verbose_name = 'Количество выходов DVI')
  hdmi                   = models.PositiveSmallIntegerField (                               verbose_name = 'Количество выходов HDMI')
  comment                = models.CharField                 (max_length = 100, null = True, verbose_name = 'Комментарий')

class Hosts (models.Model):
  '''Хосты'''
  class Meta (object):
    db_table = 'hosts'

  id        = models.AutoField    (primary_key = True,                                                         verbose_name = 'Код')
  cpu       = models.ForeignKey   ('CPUs',     on_delete = models.SET_NULL, null = True, db_column = 'cpu',    verbose_name = 'Процессор')
  mother    = models.ForeignKey   ('Mothers',  on_delete = models.SET_NULL, null = True, db_column = 'mother', verbose_name = 'Материнская плата')
  ram1      = models.ForeignKey   ('RAMs',     on_delete = models.SET_NULL, null = True, db_column = 'ram1',   verbose_name = 'ОП1',        related_name = 'ram1')
  ram2      = models.ForeignKey   ('RAMs',     on_delete = models.SET_NULL, null = True, db_column = 'ram2',   verbose_name = 'ОП2',        related_name = 'ram2')
  ram3      = models.ForeignKey   ('RAMs',     on_delete = models.SET_NULL, null = True, db_column = 'ram3',   verbose_name = 'ОП3',        related_name = 'ram3')
  ram4      = models.ForeignKey   ('RAMs',     on_delete = models.SET_NULL, null = True, db_column = 'ram4',   verbose_name = 'ОП4',        related_name = 'ram4')
  hdd1      = models.ForeignKey   ('HDDs',     on_delete = models.SET_NULL, null = True, db_column = 'hdd1',   verbose_name = 'Винчестер1', related_name = 'hdd1')
  hdd2      = models.ForeignKey   ('HDDs',     on_delete = models.SET_NULL, null = True, db_column = 'hdd2',   verbose_name = 'Винчестер2', related_name = 'hdd2')
  hdd3      = models.ForeignKey   ('HDDs',     on_delete = models.SET_NULL, null = True, db_column = 'hdd3',   verbose_name = 'Винчестер3', related_name = 'hdd3')
  hdd4      = models.ForeignKey   ('HDDs',     on_delete = models.SET_NULL, null = True, db_column = 'hdd4',   verbose_name = 'Винчестер4', related_name = 'hdd4')
  net       = models.ForeignKey   ('Networks', on_delete = models.SET_NULL, null = True, db_column = 'net',    verbose_name = 'Сетевая карта')
  video     = models.ForeignKey   ('Videos',   on_delete = models.SET_NULL, null = True, db_column = 'video',  verbose_name = 'Видеокарта')
  name      = models.CharField    (max_length = 30,                                                            verbose_name = 'Сетевое имя')
  ip        = models.CharField    (max_length = 15,                                                            verbose_name = 'IP-адрес')
  comment   = models.CharField    (max_length = 100,                        null = True,                       verbose_name = 'Комментарий')

class StaffLog (models.Model):
  '''Логирование обслуживания'''
  class Meta (object):
    db_table = 'staff_log'

  id   = models.AutoField  (primary_key = True)
  host = models.ForeignKey ('Hosts',      on_delete = models.SET_NULL, null = True, db_column = 'host', verbose_name = 'Хост')
  type = models.ForeignKey ('StaffTypes', on_delete = models.SET_NULL, null = True, db_column = 'type', verbose_name = 'Тип обслуживания')
  date = models.DateField  (                                                                            verbose_name = 'Дата')
  time = models.TimeField  (                                                                            verbose_name = 'Время')

class StaffTypes (models.Model):
  '''Типы обслуживания'''
  class Meta (object):
    db_table = 'staff_types'

  id      = models.AutoField (primary_key = True)
  name    = models.CharField (max_length =   5, verbose_name = 'Название')
  comment = models.CharField (max_length = 100, verbose_name = 'Комментарий')

class StaffPeriods (models.Model):
  '''Периоды обслуживания'''
  class Meta (object):
    db_table = 'staff_periods'

  id     = models.AutoField    (primary_key = True)
  host   = models.ForeignKey   ('Hosts',      on_delete = models.SET_NULL, null = True, db_column = 'host', verbose_name = 'Хост')
  type   = models.ForeignKey   ('StaffTypes', on_delete = models.SET_NULL, null = True, db_column = 'type', verbose_name = 'Тип обслуживания')
  period = models.IntegerField (                                           null = False,                    verbose_name = 'Период обслуживания')

