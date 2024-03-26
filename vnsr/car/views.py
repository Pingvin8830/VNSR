from django.shortcuts import render
from django.views import generic
from django.contrib.auth.mixins import LoginRequiredMixin

from vnsr.settings import DEBUG
from .models import Refuel

# Create your views here.
class Index(LoginRequiredMixin, generic.TemplateView):
  template_name = 'car/index.html'

  def get_context_data(self, **kwargs):
    context = super().get_context_data(**kwargs)
    context['debug'] = DEBUG
    context['active_app'] = 'car'
    return context

class Refuels(LoginRequiredMixin, generic.ListView):
  raise_exception = True
  model = Refuel
  context_object_name = 'refuels'
  template_name = 'car/refuels.html'

  def get_context_data(self, **kwargs):
    context = super().get_context_data(**kwargs)
    context['debug'] = DEBUG
    context['active_app'] = 'car'
    context['active_page'] = 'refuels'
    return context

class RefuelView(LoginRequiredMixin, generic.DetailView):
  raise_exception = True
  model = Refuel
  template_name = 'car/refuel.html'

  def get_context_data(self, **kwargs):
    context = super().get_context_data(**kwargs)
    context['debug'] = DEBUG
    context['active_app'] = 'car'
    context['active_page'] = 'refuels'
    return context

