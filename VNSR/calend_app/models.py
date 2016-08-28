from django.db import models

# Create your models here.
class Signs (models.Model):
	'''
		Признаки дней
	'''

	class Meta ():
		db_table = 'signs'

	id      = models.AutoField    (primary_key = True)
	data    = models.DateField    (unique      = True)
	work    = models.BooleanField (default     = True)
	week    = models.BooleanField (default     = False)
	holiday = models.BooleanField (default     = False)
	short   = models.BooleanField (default     = False)
	comment = models.CharField    (max_length  = 100)

	def get_class (self):
		if   self.work:    return 'work'
		elif self.week:    return 'week'
		elif self.holiday: return 'holiday'
		elif self.short:   return 'short'
		else:              return 'work'
