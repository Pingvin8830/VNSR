from django.contrib import admin

from .models import Travel, TravelState, Point, Place, Way, TollRoad, Hotel

# Register your models here.
class PointInline(admin.TabularInline):
  model = Point
  extra = 3
  classes = ['collapse']

class WayInline(admin.TabularInline):
  model = Way
  extra = 2
  classes = ['collapse']

class TollRoadInline(admin.TabularInline):
  model = TollRoad
  extra = 1
  classes = ['collapse']

class HotelInline(admin.TabularInline):
  model = Hotel
  extra = 1
  classes = ['collapse']

class TravelAdmin(admin.ModelAdmin):
  fieldsets = [
    ('Основные сведения', {'fields': ['name', 'state', 'participants']}),
    ('Топливо', {'fields': ['fuel_consumption', 'fuel_price'], 'classes': ['collapse']})
  ]
  inlines = [PointInline, WayInline, TollRoadInline, HotelInline]

admin.site.register(Place)
admin.site.register(TravelState)
admin.site.register(Travel, TravelAdmin)

