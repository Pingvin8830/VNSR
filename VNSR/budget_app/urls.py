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
  url (r'^add_card',      views.add_card,      name = 'add_card'),
  url (r'^display_cards', views.display_cards, name = 'display_cards'),
  url (r'^set_cards',     views.set_cards,     name = 'set_cards'),
  url (r'^',              views.index,         name = 'index'),
]
