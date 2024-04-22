from django.db import models

# Create your models here.
class Object(models.Model):
  class Meta:
    verbose_name = 'Объект'
    verbose_name_plural = 'Объекты'

  name = models.CharField(max_length=50)
  object_id = models.IntegerField()
  field = models.CharField(max_length=50)
  value = models.CharField(max_length=255)

#{'object': 'StreetType', 'id': 6, 'name': 'Test street type', 'short': 'tst'}
#{'object': 'CityType', 'id': 4, 'name': 'Test city type', 'short': 'tct'}
#{'object': 'Region', 'id': 6, 'code': '00', 'name': 'Test region'}
#{'object': 'Street', 'id': 11, 'type_id': 6, 'name': 'Test street'}
#{'object': 'City', 'id': 6, 'type_id': 4, 'name': 'Test city'}
#{'object': 'Address', 'id': 12, 'region_id': 6, 'city_id': 6, 'street_id': 11, 'house': '1', 'building': '', 'flat': 0, 'name': 'Test address'}
#{'object': 'Fuel', 'id': 4, 'name': 'Test fuel'}
#{'object': 'FuelStation', 'id': 5, 'company': 'Test company', 'number': '1', 'phone': '1', 'address_id': 12}
#{'object': 'Refuel', 'id': 4, 'check_number': 1, 'date_time': '21-04-2024 20:19', 'price': 1, 'count': 1, 'cost': 1, 'distance': 1, 'fuel_id': 4, 'distance_reserve': 1, 'fuel_consumption': 1, 'fuel_consumption_avg': 1, 'fuel_station_id': 5, 'odometer': 1, 'time_delta': '1', 'trk': 1}
