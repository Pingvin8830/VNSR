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

app_name    = 'calend_app' 
urlpatterns = [
	url (r'^(?P<year>[0-9]{4,4})/(?P<month>[0-9]{1,2})/$',         views.display_calend, name = 'index'),
	url (r'^(?P<year>[0-9]{4,4})/(?P<month>[0-9]{1,2})/display/$', views.display_signs,  name = 'display_signs'),
	url (r'^(?P<year>[0-9]{4,4})/(?P<month>[0-9]{1,2})/set/$',     views.set_signs,      name = 'set_signs'),
	url (r'^$',                                                    views.display_calend, {'year': 2016, 'month': 8}),
]