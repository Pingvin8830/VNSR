class CalendRouter (object):
	'''
		Перенаправление в БД calend
	'''

	def db_for_read (self, model, **hints):
		if model._meta.app_label == 'calend_app':
			return 'calend_db'
		return None

	def db_for_write (self, model, **hints):
		if model._meta.app_label == 'calend_app':
			return 'calend_db'
		return None

	def allow_relation (self, obj1, obj2, **hints):
		if	obj1._meta.app_label == 'calend_app' or \
				obj2._meta.app_label == 'calend_app':
					return True
		return None

	def allow_migrate (self, db, app_label, model = None, **hints):
		if app_label == 'calend_app':
			return db == 'calend_db'
		return None

class AuthRouter (object):
	'''
		Перенаправление в БД auth
	'''

	def db_for_read (self, model, **hints):
		if model._meta.app_label == 'auth':
			return 'auth_db'
		return None

	def db_for_write (self, model, **hints):
		if model._meta.app_label == 'auth':
			return 'auth_db'
		return None

	def allow_relation (self, obj1, obj2, **hints):
		if	obj1._meta.app_label == 'auth' or \
				obj2._meta.app_label == 'auth':
					return True
		return None

	def allow_migrate (self, db, app_label, model = None, **hints):
		if app_label == 'auth':
			return db == 'auth_db'
		return None

