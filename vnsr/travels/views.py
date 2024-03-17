from django.shortcuts import render
from django.http import Http404

from .models import Travel

# Create your views here.
def index(request):
  travels = Travel.objects.all()
  context = {
    'travels': travels,
  }
  return render(request, 'travels/index.html', context)

def detail(request, travel_id):
  try:
    travel = Travel.objects.get(pk=travel_id)
  except Travel.DoesNotExist:
    raise Http404('Такого путешествия нет')
  return render(request, 'travels/detail.html', {'travel': travel})

