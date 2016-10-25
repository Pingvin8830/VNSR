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

app_name    = 'metro_app' 
urlpatterns = [
	url (r'^set_shedule/(?P<data>[0-9]{4,4}-[0-9]{1,2}-[0-9]{1,2})', views.set_shedule,    name = 'set_shedule'),
	url (r'^set_shift',                                              views.set_shift,      name = 'set_shift'),
	url (r'^set_work_plane',                                         views.set_work_plane, name = 'set_work_plane'),
	url (r'^',                                                       views.index,          name = 'index'),
]
