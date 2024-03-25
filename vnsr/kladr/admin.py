from django.contrib import admin

from .models import Region, City, CityType, Street, StreetType, Address

# Register your models here.
class AddressAdmin(admin.ModelAdmin):
  list_display = ['name', 'city', 'street', 'house', 'building', 'flat']

admin.site.register(Region)
admin.site.register(City)
admin.site.register(CityType)
admin.site.register(Street)
admin.site.register(StreetType)
admin.site.register(Address, AddressAdmin)
