from django.db import models

# Create your models here.
class WorkPlane (models.Model):
	'''План смен'''
	class Meta ():
		db_table = 'work_plane'
		unique_together = (('date_start', 'code'))

	id          = models.AutoField (primary_key = True)
	date_start  = models.DateField ()
	date_end    = models.DateField ()
	code        = models.CharField (max_length = 5)
	start       = models.TimeField ()
	end         = models.TimeField ()
	break_day   = models.SmallIntegerField (default = 2)
	break_night = models.SmallIntegerField (default = 0)

class ShedulePlane (models.Model):
	'''Планируемый график'''
	class Meta ():
		db_table = 'shedule_plane'
	data  = models.DateField (primary_key = True)
	shift = models.CharField (max_length  = 5)
