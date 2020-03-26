from django.urls import reverse_lazy
from django.views import generic
from . import forms, models

# Create your views here.
class Index(generic.TemplateView):
  http_method_names = ['get']
  template_name = 'sheduler/index.html'

