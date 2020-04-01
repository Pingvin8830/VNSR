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
from django.urls import include, path
from . import views

app_name = 'sheduler'
urlpatterns = [
  path('add', views.AddIssues.as_view(), name='add'),
  path('dependences/', include('sheduler.dependences.urls', namespace='dependences')),
  path('details/', include('sheduler.details.urls', namespace='details')),
  path('humans/', include('sheduler.humans.urls', namespace='humans')),
  path('locations/', include('sheduler.locations.urls', namespace='locations')),
  path('tasks/', include('sheduler.tasks.urls', namespace='tasks')),
  path('', views.CurrentIssues.as_view(), name='current_issues'),
]
