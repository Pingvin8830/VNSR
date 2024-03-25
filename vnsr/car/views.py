from django.shortcuts import render
from django.views import generic
from django.contrib.auth.mixins import LoginRequiredMixin

from .models import Refuel

# Create your views here.
class Index(LoginRequiredMixin, generic.TemplateView):
  template_name = 'car/index.html'

class Refuels(LoginRequiredMixin, generic.ListView):
  raise_exception = True
  model = Refuel
  context_object_name = 'refuels'
  template_name = 'car/refuels.html'

