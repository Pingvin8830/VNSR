from django.shortcuts import render, get_object_or_404
from django.views import generic
from django.contrib.auth.mixins import LoginRequiredMixin

from .models import Travel

# Create your views here.
class IndexView(LoginRequiredMixin, generic.ListView):
  template_name = 'travels/index.html'
  context_object_name = 'travels'

  def get_queryset(self):
    return Travel.objects.all()

class DetailView(LoginRequiredMixin, generic.DetailView):
  raise_exception = True
  model = Travel
  template_name = 'travels/detail.html'

