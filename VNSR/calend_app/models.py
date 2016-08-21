from django.db import models

# Create your models here.
class Signs (models.Model):
	'''
		Признаки дней
	'''

	class Meta ():
		db_table = 'signs'

	data    = models.DateField    (primary_key = True )
	work    = models.BooleanField (default     = True )
	week    = models.BooleanField (default     = False)
	holiday = models.BooleanField (default     = False)
	short   = models.BooleanField (default     = False)
	comment = models.CharField    (max_length  = 100)
