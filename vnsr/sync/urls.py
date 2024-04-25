from django.urls import path

from . import views

app_name='sync'
urlpatterns = [
  path('truncate', views.truncate, name='truncate'),
  path('send',     views.send,     name='send'),
  path('start',    views.start,    name='start'),
  path('get',      views.get,      name='get'),
  path('',         views.index,    name='index'),
]
