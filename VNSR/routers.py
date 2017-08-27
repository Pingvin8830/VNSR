MY_APPS = [
  'budget_app',
  'calend_app',
  'car_app',
  'computers_app',
  'hrefs_app',
  'menu_app',
  'metro_app',
]

MY_DB = [
  'budget_db',
  'calend_db',
  'car_db',
  'computers_db',
  'hrefs_db',
  'menu_db',
  'metro_db',
]

class BudgetRouter (object):
  '''Перенаправление в БД budget'''
  def db_for_read (self, model, **hints):
    if model._meta.app_label == 'budget_app': return 'budget_db'
    return None
  def db_for_write (self, model, **hints):
    if model._meta.app_label == 'budget_app': return 'budget_db'
    return None
  def allow_relation (self, obj1, obj2, **hints):
    if  obj1._meta.app_label == 'budget_app' or \
        obj2._meta.app_label == 'budget_app':
          return True
    return None
  def allow_migrate (self, db, app_label, model = None, **hints):
    if app_label == 'budget_app': return db == 'budget_db'
    return None

class CalendRouter (object):
  '''Перенаправление в БД calend'''
  def db_for_read (self, model, **hints):
    if model._meta.app_label == 'calend_app': return 'calend_db'
    return None
  def db_for_write (self, model, **hints):
    if model._meta.app_label == 'calend_app': return 'calend_db'
    return None
  def allow_relation (self, obj1, obj2, **hints):
    if	obj1._meta.app_label == 'calend_app' or \
        obj2._meta.app_label == 'calend_app':
          return True
    return None
  def allow_migrate (self, db, app_label, model = None, **hints):
    if app_label == 'calend_app': return db == 'calend_db'
    return None

class CarRouter (object):
  '''Перенаправление в БД car'''
  def db_for_read (self, model, **hints):
    if model._meta.app_label == 'car_app': return 'car_db'
    return None
  def db_for_write (self, model, **hints):
    if model._meta.app_label == 'car_app': return 'car_db'
    return None
  def allow_relation (self, obj1, obj2, **hints):
    if  obj1._meta.app_label == 'car_app' or \
        obj2._meta.app_label == 'car_app':
          return True
    return None
  def allow_migrate (self, db, app_label, model = None, **hints):
    if app_label == 'car_app': return db == 'car_db'
    return None

class ComputersRouter (object):
  '''Перенаправление в БД computers'''
  def db_for_read (self, model, **hints):
    if model._meta.app_label == 'computers_app': return 'computers_db'
    return None
  def db_for_write (self, model, **hints):
    if model._meta.app_label == 'computers_app': return 'computers_db'
    return None
  def allow_relation (self, obj1, obj2, **hints):
    if  obj1._meta.app_label == 'computers_app' or \
        obj2._meta.app_label == 'computers_app':
          return True
    return None
  def allow_migrate (self, db, app_label, model = None, **hints):
    if app_label == 'computers_app': return db == 'computers_db'
    return None

class HrefsRouter (object):
  '''Перенаправление в БД hrefs'''
  def db_for_read (self, model, **hints):
    if model._meta.app_label == 'hrefs_app': return 'hrefs_db'
    return None
  def db_for_write (self, model, **hints):
    if model._meta.app_label == 'hrefs_app': return 'hrefs_db'
    return None
  def allow_relation (self, obj1, obj2, **hints):
    if  obj1._meta.app_label == 'hrefs_app' or \
        obj2._meta.app_label == 'hrefs_app':
          return True
    return None
  def allow_migrate (self, db, app_label, model = None, **hints):
    if app_label == 'hrefs_app': return db == 'hrefs_db'
    return None

class MenuRouter (object):
  '''Перенаправление в БД menu'''
  def db_for_read (self, model, **hints):
    if model._meta.app_label == 'menu_app': return 'menu_db'
    return None
  def db_for_write (self, model, **hints):
    if model._meta.app_label == 'menu_app': return 'menu_db'
    return None
  def allow_relation (self, obj1, obj2, **hints):
    if	obj1._meta.app_label == 'menu_app' or \
        obj2._meta.app_label == 'menu_app':
          return True
    return None
  def allow_migrate (self, db, app_label, model = None, **hints):
    if app_label == 'menu_app': return db == 'menu_db'
    return None

class MetroRouter (object):
  '''Перенаправление в БД metro'''
  def db_for_read (self, model, **hints):
    if model._meta.app_label == 'metro_app': return 'metro_db'
    return None
  def db_for_write (self, model, **hints):
    if model._meta.app_label == 'metro_app': return 'metro_db'
    return None
  def allow_relation (self, obj1, obj2, **hints):
    if	obj1._meta.app_label == 'metro_app' or \
        obj2._meta.app_label == 'metro_app':
          return True
    return None
  def allow_migrate (self, db, app_label, model = None, **hints):
    if app_label == 'metro_app': return db == 'metro_db'
    return None

class DefaultRouter (object):
  '''Перенаправление в БД default'''
  def db_for_read (self, model, **hints):
    return 'default'
  def db_for_write (self, model, **hints):
    return 'default'
  def allow_relation (self, obj1, obj2, **hints):
    return True
  def allow_migrate (self, db, app_label, model = None, **hints):
    if	app_label not in MY_APPS and \
        db        not in MY_DB: 
      return True
    return False
