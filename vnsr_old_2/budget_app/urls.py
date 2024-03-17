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

app_name    = 'budget_app' 
urlpatterns = [
  url (r'^add_card',                         views.add_card,            name = 'add_card'),
  url (r'^add_cheque',                       views.add_cheque,          name = 'add_cheque'),
  url (r'^add_credit/(?P<cheque_id>[0-9]+)', views.add_credit,          name = 'add_credit'),
  url (r'^add_cred_cat',                     views.add_cred_cat,        name = 'add_cred_cat'),
  url (r'^add_debet_type',                   views.add_debet_type,      name = 'add_debet_type'),
  url (r'^add_debet',                        views.add_debet,           name = 'add_debet'),
  url (r'^add_org_type',                     views.add_org_type,        name = 'add_org_type'),
  url (r'^add_org',                          views.add_org,             name = 'add_org'),
  url (r'^add_product',                      views.add_product,         name = 'add_product'),
  url (r'^case_cheque',                      views.case_cheque,         name = 'case_cheque'),
  url (r'^display_cards',                    views.display_cards,       name = 'display_cards'),
  url (r'^display_credits',                  views.display_credits,     name = 'display_credits'),
  url (r'^display_debets',                   views.display_debets,      name = 'display_debets'),
  url (r'^display_debet_types',              views.display_debet_types, name = 'display_debet_types'),
  url (r'^display_org_types',                views.display_org_types,   name = 'display_org_types'),
  url (r'^set_cards',                        views.set_cards,           name = 'set_cards'),
  url (r'^set_debet_types',                  views.set_debet_types,     name = 'set_debet_types'),
  url (r'^set_org_types',                    views.set_org_types,       name = 'set_org_types'),
  url (r'^',                                 views.index,               name = 'index'),
]
