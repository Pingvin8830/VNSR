#from django.shortcuts import render
from django.views.decorators.csrf import csrf_exempt
from django.http import JsonResponse, Http404

#from vnsr.settings import DEBUG
from kladr.models import StreetType, CityType, Region, Street, City, Address
from car.models import Fuel, FuelStation, Refuel
from travels.models import TravelState, Travel, Point

from .models import Object

import json

# Create your views here.
@csrf_exempt
def index(request):
  return Http404

@csrf_exempt
def send(request):
  if request.method != 'POST': return JsonResponse({'state': 'error'}, status=400)
  request_data = json.loads(request.body.decode('utf8'))
  object_name = request_data['object']
  object_id = request_data['id']
  for key, value in request_data.items():
    if key == 'object' or key == 'id': continue
    object_to_sync = Object(
      name = object_name,
      object_id = object_id,
      field = key,
      value = value
    )
    object_to_sync.save()
  response_data = {
    'object': request_data['object'],
    'id': request_data['id'],
    'state': 'Ok'
  }
  return JsonResponse(response_data)

@csrf_exempt
def truncate(request):
  if request.method != 'POST': return JsonResponse({'state': 'error'}, status=400)
  Object.objects.all().delete()
  return JsonResponse({'truncated': 'Ok'})

@csrf_exempt
def start(request):
  if request.method != 'POST': return JsonResponse({'state': 'error'}, status=400)
  objects = {}
  for object in Object.objects.all():
    if object.name not in objects: objects[object.name] = []
    if object.object_id not in objects[object.name]: objects[object.name].append(object.object_id)

  for object in objects:
    for id in objects[object]:
      target = {'object_name': object, 'object_id': id}
      tasks = Object.objects.filter(name=object).filter(object_id=id)
      for task in tasks: target[task.field] = task.value

      match object:
        case 'StreetType':
          street_type = StreetType()
          street_type.load(target)
          try: find_object = StreetType.objects.get(name=target['name'])
          except StreetType.DoesNotExist: street_type.save()
        case 'CityType':
          city_type = CityType()
          city_type.load(target)
          try: find_object = CityType.objects.get(name=target['name'])
          except CityType.DoesNotExist: city_type.save()
        case 'Region':
          region = Region()
          region.load(target)
          try: find_object = Region.objects.get(code=target['code'])
          except Region.DoesNotExist: region.save()
        case 'Street':
          street = Street()
          street.load(target)
          try: find_object = Street.objects.get(name=target['name'], type__name=target['type_name'])
          except Street.DoesNotExist: street.save()
        case 'City':
          city = City()
          city.load(target)
          try: find_object = City.objects.get(name=target['name'])
          except City.DoesNotExist: city.save()
        case 'Address':
          address = Address()
          address.load(target)
          try: find_object = Address.objects.get(name=target['name'])
          except Address.DoesNotExist: address.save()
        case 'Fuel':
          fuel = Fuel()
          fuel.load(target)
          try: find_object = Fuel.objects.get(name=target['name'])
          except Fuel.DoesNotExist: fuel.save()
        case 'FuelStation':
          fuel_station = FuelStation()
          fuel_station.load(target)
          try: find_object = FuelStation.objects.get(company=target['company'], number=target['number'])
          except FuelStation.DoesNotExist: fuel_station.save()
        case 'Refuel':
          refuel = Refuel()
          refuel.load(target)
          try:
            correct_datetime = target['datetime'][6:10] + '-' + target['datetime'][3:5] + '-' + target['datetime'][:2] + ' ' + target['datetime'][11:16]
            find_object = Refuel.objects.get(datetime=correct_datetime)
          except Refuel.DoesNotExist: refuel.save()
        case 'TravelState':
          travel_state = TravelState()
          travel_state.load(target)
          try: find_object = TravelState.objects.get(name=target['name'])
          except TravelState.DoesNotExist: travel_state.save()
        case 'Travel':
          travel = Travel()
          travel.load(target)
          try: find_object = Travel.objects.get(name=target['name'])
          except Travel.DoesNotExist: travel.save()
        case 'Point':
          point = Point()
          point.load(target)
          try:
            correct_datetime = target['datetime'][6:10] + '-' + target['datetime'][3:5] + '-' + target['datetime'][:2] + ' ' + target['datetime'][11:16]
            find_object = Point.objects.get(datetime=correct_datetime)
          except Point.DoesNotExist: point.save()

  return JsonResponse({'state': 'started'})

@csrf_exempt
def get(request):
  if request.method != 'POST': return JsonResponse({'state': 'error'}, status=400)
  request_data = json.loads(request.body.decode('utf8'))
  value = None
  match request_data.get('data'):
    case 'Count':
      match request_data.get('object_name'):
        case 'all': value = StreetType.objects.count() + CityType.objects.count() + Region.objects.count() + Street.objects.count() + City.objects.count() + Address.objects.count() + Fuel.objects.count() + FuelStation.objects.count() + Refuel.objects.count() + TravelState.objects.count() + Travel.objects.count() + Point.objects.count()
        case 'StreetType':  value = StreetType.objects.count()
        case 'CityType':    value = CityType.objects.count()
        case 'Street':      value = Street.objects.count()
        case 'City':        value = City.objects.count()
        case 'Region':      value = Region.objects.count()
        case 'Address':     value = Address.objects.count()
        case 'Fuel':        value = Fuel.objects.count()
        case 'FuelStation': value = FuelStation.objects.count()
        case 'Refuel':      value = Refuel.objects.count()
        case 'TravelState': value = TravelState.objects.count()
        case 'Travel':      value = Travel.objects.count()
        case 'Point':       value = Point.objects.count()
    case 'all':
      match request_data.get('object_name'):
        case 'StreetType':
          value = []
          for street_type in StreetType.objects.all(): value.append(street_type.to_json())
        case 'CityType':
          value = []
          for city_type in CityType.objects.all(): value.append(city_type.to_json())
        case 'Street':
          value = []
          for street in Street.objects.all(): value.append(street.to_json())
        case 'City':
          value = []
          for city in City.objects.all(): value.append(city.to_json())
        case 'Region':
          value = []
          for region in Region.objects.all(): value.append(region.to_json())
        case 'Address':
          value = []
          for address in Address.objects.all(): value.append(address.to_json())
        case 'Fuel':
          value = []
          for fuel in Fuel.objects.all(): value.append(fuel.to_json())
        case 'FuelStation':
          value = []
          for fuel_station in FuelStation.objects.all(): value.append(fuel_station.to_json())
        case 'Refuel':
          value = []
          for refuel in Refuel.objects.all(): value.append(refuel.to_json())
        case 'TravelState':
          value = []
          for travel_state in TravelState.objects.all(): value.append(travel_state.to_json())
        case 'Travel':
          value = []
          for travel in Travel.objects.all(): value.append(travel.to_json())
        case 'Point':
          value = []
          for point in Point.objects.all(): value.append(point.to_json())
  response_data = {'object_name': request_data['object_name'], 'data': request_data['data'], 'value': value}
  return JsonResponse(response_data)
