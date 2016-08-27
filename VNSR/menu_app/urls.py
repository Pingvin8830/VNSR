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

app_name    = 'menu_app' 
urlpatterns = [
	url (r'^case_user$',                         views.case_user,         name = 'case_user'),
	url (r'^case_app$',                          views.case_app,          name = 'case_app'),
	url (r'^set_app$',                           views.set_app,           name = 'set_app'),
	url (r'^add_app$',                           views.add_app,           name = 'add_app'),
	url (r'^set_item$',                          views.set_item,          name = 'set_item'),
	url (r'^add_item$',                          views.add_item,          name = 'add_item'),
	url (r'^set_user$',                          views.set_user,          name = 'set_user'),
	url (r'^add_user$',                          views.add_user,          name = 'add_user'),
	url (r'^set_user_menu/(?P<user_id>[0-9]+)$', views.set_user_menu,     name = 'set_user_menu'),
	url (r'^display_user_menu$',                 views.display_user_menu, name = 'display_user_menu'),
	url (r'^$',                                  views.index,             name = 'index'),
]
