from django.urls import path

from . import views

app_name = 'car'
urlpatterns = [
  path('refuels', views.Refuels.as_view(), name='refuels'),
  path('', views.Index.as_view(), name='index'),
]
