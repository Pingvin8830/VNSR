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
  url (r'^add_payslip_code',                   views.add_payslip_code,      name = 'add_payslip_code'),
  url (r'^add_payslip_details/(?P<id>[0-9]+)', views.add_payslip_details, name = 'add_payslip_details'),
  url (r'^add_payslip',                        views.add_payslip,           name = 'add_payslip'),
  url (r'^add_shedule_real',                   views.add_shedule_real,      name = 'add_shedule_real'),
  url (r'^display_payslip_codes',              views.display_payslip_codes, name = 'display_payslip_codes'),
  url (r'^display_payslips',                   views.display_payslips,      name = 'display_payslips'),
  url (r'^display_tabel',                      views.display_tabel,         name = 'display_tabel'),
  url (r'^',                                   views.index,                 name = 'index'),
#  url (r'^control_payslip/(?P<id>[0-9]+)', views.control_payslip,       name = 'control_payslip'),
]

