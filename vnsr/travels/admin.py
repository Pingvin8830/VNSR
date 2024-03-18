from django.contrib import admin

from .models import Travel, TravelState, Point, Place, Way, TollRoad, Hotel

# Register your models here.
admin.site.register(Place)
admin.site.register(Point)
admin.site.register(TravelState)
admin.site.register(Travel)
admin.site.register(Way)
admin.site.register(TollRoad)
admin.site.register(Hotel)
