"""vnsr URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/3.0/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.urls import path
from . import views

app_name = 'calend'
urlpatterns = [
  path('<int:year>/<int:month>',views.Month.as_view(), name='month'),
  path('<int:year>',views.Year.as_view(), name='year'),
  path('add_mark', views.AddMark.as_view(), name='add_mark'),
  path('add_sign', views.AddSign.as_view(), name='add_sign'),
  path('set_production/<int:year>/<int:month>', views.SetProduction.as_view(), name='set_production'),
  path('set_production/', views.SetProduction.as_view(), name='set_production'),
  path('', views.Index.as_view(), name='index'),
]
