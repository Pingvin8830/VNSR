from django.shortcuts import render

from .models import Travel

# Create your views here.
def index(request):
  travels = Travel.objects.all()
  context = {
    'travels': travels,
  }
  return render(request, 'travels/index.html', context)

def detail(request, travel_id):
  return HttpResponse(f'Детализация поездки {travel_id}')

