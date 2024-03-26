from django.shortcuts import render, get_object_or_404
from django.views import generic
from django.contrib.auth.mixins import LoginRequiredMixin

from vnsr.settings import DEBUG
from .models import Travel

# Create your views here.
class IndexView(LoginRequiredMixin, generic.ListView):
  template_name = 'travels/index.html'
  context_object_name = 'travels'

  def get_queryset(self):
    return Travel.objects.all()

  def get_context_data(self, **kwargs):
    context = super().get_context_data(**kwargs)
    context['debug'] = DEBUG
    context['active_app'] = 'travels'
    return context

class DetailView(LoginRequiredMixin, generic.DetailView):
  raise_exception = True
  model = Travel
  template_name = 'travels/detail.html'

  def get_context_data(self, **kwargs):
    context = super().get_context_data(**kwargs)
    context['debug'] = DEBUG
    context['active_app'] = 'travels'
    return context
