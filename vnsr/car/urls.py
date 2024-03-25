from django.urls import path

from . import views

app_name = 'car'
urlpatterns = [
  path('refuels/<int:pk>', views.RefuelView.as_view(), name='refuel'),
  path('refuels', views.Refuels.as_view(), name='refuels'),
  path('', views.Index.as_view(), name='index'),
]
