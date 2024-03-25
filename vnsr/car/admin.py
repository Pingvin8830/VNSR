from django.contrib import admin

from .models import Refuel, FuelStation, Fuel

# Register your models here.
class RefuelAdmin(admin.ModelAdmin):
  fieldsets = [
    ('АЗС', {'fields': ['fuel_station', 'check_number', 'datetime', 'trk', 'fuel', 'count', 'price', 'cost']}),
    ('Авто', {'fields': ['distance_reserve', 'fuel_consumption_avg', 'odometer', 'distance', 'fuel_consumption', 'timedelta']})
  ]
  inlines = []
  list_display = ['datetime', 'fuel_station', 'fuel', 'count', 'price', 'cost']

admin.site.register(FuelStation)
admin.site.register(Fuel)
admin.site.register(Refuel, RefuelAdmin)
