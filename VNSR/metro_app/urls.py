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
  url (r'^add_codes_payslip',                                      views.add_codes_payslip,       name = 'add_codes_payslip'),
  url (r'^add_details/(?P<id>[0-9]+)',                             views.add_details,             name = 'add_details'),
  url (r'^add_payslip',                                            views.add_payslip,             name = 'add_payslip'),
  url (r'^case_month',                                             views.case_month,              name = 'case_month'),
  url (r'^display_payslip',                                        views.display_payslip,         name = 'display_payslip'),
  url (r'^set_codes_payslip',                                      views.set_codes_payslip,       name = 'set_codes_payslip'),
  url (r'^set_shedule/(?P<data>[0-9]{4,4}-[0-9]{1,2}-[0-9]{1,2})', views.set_shedule,             name = 'set_shedule'),
  url (r'^set_shift',                                              views.set_shift,               name = 'set_shift'),
  url (r'^set_work_plane',                                         views.set_work_plane,          name = 'set_work_plane'),
  url (r'^',                                                       views.index,                   name = 'index'),
]
