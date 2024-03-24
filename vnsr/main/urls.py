from django.urls import path

from . import views

app_name='main'
urlpatterns = [
  path('login/', views.Login.as_view(), name='login'),
  path('logout/', views.Logout.as_view(), name='logout'),
  path('', views.Index.as_view(), name='index'),
]
