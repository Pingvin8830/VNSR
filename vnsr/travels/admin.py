from django.contrib import admin

from .models import Travel, TravelState

# Register your models here.
admin.site.register(TravelState)
admin.site.register(Travel)

