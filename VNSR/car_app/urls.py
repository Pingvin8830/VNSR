"""VNSR URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/1.9/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  url(r'^$', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  url(r'^$', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.conf.urls import url, include
    2. Add a URL to urlpatterns:  url(r'^blog/', include('blog.urls'))
"""
from django.conf.urls import url
from .                import views

app_name    = 'car_app' 
urlpatterns = [
  url (r'^add_azs',                                    views.add_azs,              name = 'add_azs'),
  url (r'^add_check_point/(?P<travel_id>[0-9]*)',      views.add_check_point,      name = 'add_check_point'),
  url (r'^add_refuel',                                 views.add_refuel,           name = 'add_refuel'),
  url (r'^add_travel',                                 views.add_travel,           name = 'add_travel'),
  url (r'^display_azs',                                views.display_azs,          name = 'display_azs'),
  url (r'^display_check_points/(?P<travel_id>[0-9]*)', views.display_check_points, name = 'display_check_points'),
  url (r'^display_fuel_types',                         views.display_fuel_types,   name = 'display_fuel_types'),
  url (r'^display_travels',                            views.display_travels,      name = 'disply_travels'),
  url (r'^',                                           views.index,                name = 'index'),
]

