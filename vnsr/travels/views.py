from django.shortcuts import render
from django.http import HttpResponse
from django.template import loader

from .models import Travel

# Create your views here.
def index(request):
  travels = Travel.objects.all()
  template = loader.get_template('travels/index.html')
  context = {
    'travels': travels,
  }
  return HttpResponse(template.render(context, request))

def detail(request, travel_id):
  return HttpResponse(f'Детализация поездки {travel_id}')

