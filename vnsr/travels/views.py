from django.shortcuts import render, get_object_or_404
from django.views import generic

from .models import Travel

# Create your views here.
class IndexView(generic.ListView):
  template_name = 'travels/index.html'
  context_object_name = 'travels'

  def get_queryset(self):
    return Travel.objects.all()

class DetailView(generic.DetailView):
  model = Travel
  template_name = 'travels/detail.html'

