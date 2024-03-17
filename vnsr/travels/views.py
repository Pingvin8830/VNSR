from django.shortcuts import render
from django.http import HttpResponse

# Create your views here.
def index(request):
  return HttpResponse('Стартовая страница приложения "Путешествия"')

def detail(request, travel_id):
  return HttpResponse(f'Детализация поездки {travel_id}')

