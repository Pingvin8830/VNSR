from django.shortcuts import render
from django.http import HttpResponse

from .models import Travel

# Create your views here.
def index(request):
  travels = Travel.objects.all()
  output = ','.join([t.name for t in travels])
  return HttpResponse(output)

def detail(request, travel_id):
  return HttpResponse(f'Детализация поездки {travel_id}')

