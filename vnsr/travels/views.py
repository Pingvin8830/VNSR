from django.shortcuts import render, get_object_or_404

from .models import Travel

# Create your views here.
def index(request):
  travels = Travel.objects.all()
  context = {
    'travels': travels,
  }
  return render(request, 'travels/index.html', context)

def detail(request, travel_id):
  travel = get_object_or_404(Travel, pk=travel_id)
  return render(request, 'travels/detail.html', {'travel': travel})

