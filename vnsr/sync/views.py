#from django.shortcuts import render
from django.views.decorators.csrf import csrf_exempt
from django.http import JsonResponse

#from vnsr.settings import DEBUG
from kladr.models import StreetType, CityType, Region, Street, City, Address
from car.models import Fuel, FuelStation, Refuel

from .models import Object

import json

# Create your views here.
@csrf_exempt
def index(request):
  if request.method != 'GET': return JsonResponse({'state': 'error'}, status=400)
  response_json = {
    'regions': [
      {
        'id': 1,
        'code': '00',
        'name': 'Test region 00'
      },
      {
        'id': 2,
        'code': '100',
        'name': 'Test region 100'
      }
    ],
    'city_types': [
      {
        'id': 1,
        'short': 'tct',
        'name': 'Test city type'
      },
    ],
    'cityes': [
      {
        'id': 1,
        'type': 1,
        'name': 'Test city'
      },
    ],
    'street_types': [
      {
        'id': 1,
        'short': 'tst',
        'name': 'Test street type'
      },
    ],
    'streets': [
      {
        'id': 1,
        'type': 1,
        'name': 'Test street'
      }
    ],
    'addresses': [
      {
        'id': 1,
        'region': 1,
        'city': 1,
        'street': 1,
        'house': '999',
        'building': '',
        'flat': 0
      }
    ]
  }
  return JsonResponse(data=response_json)

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
          except StreetType.DoesNotExist: find_object = None;
          if not find_object: street_type.save()
        case 'CityType':
          city_type = CityType()
          city_type.load(target)
          try: find_object = CityType.objects.get(name=target['name'])
          except CityType.DoesNotExist: find_object = None;
          if not find_object: city_type.save()
        case 'Region':
          region = Region()
          region.load(target)
          try: find_object = Region.objects.get(code=target['code'])
          except Region.DoesNotExist: find_object = None;
          if not find_object: region.save()
        case 'Street':
          street = Street()
          street.load(target)
          try: find_object = Street.objects.get(name=target['name'], type__name=target['type_name'])
          except Street.DoesNotExist: find_object = None;
          if not find_object: street.save()
        case 'City':
          city = City()
          city.load(target)
          try: find_object = City.objects.get(name=target['name'])
          except City.DoesNotExist: find_object = None;
          if not find_object: city.save()
        case 'Address':
          address = Address()
          address.load(target)
          try: find_object = Address.objects.get(name=target['name'])
          except Address.DoesNotExist: find_object = None;
          if not find_object: address.save()
        case 'Fuel':
          fuel = Fuel()
          fuel.load(target)
          try: find_object = Fuel.objects.get(name=target['name'])
          except Fuel.DoesNotExist: find_object = None;
          if not find_object: fuel.save()
        case 'FuelStation':
          fuel_station = FuelStation()
          fuel_station.load(target)
          try: find_object = FuelStation.objects.get(company=target['company'], number=target['number'])
          except FuelStation.DoesNotExist: find_object = None;
          if not find_object: fuel_station.save()
        case 'Refuel':
          refuel = Refuel()
          refuel.load(target)
          try:
            correct_datetime = target['datetime'][6:10] + '-' + target['datetime'][3:5] + '-' + target['datetime'][:2] + ' ' + target['datetime'][11:16]
            find_object = Refuel.objects.get(datetime=correct_datetime)
          except Refuel.DoesNotExist: find_object = None;
          if not find_object: refuel.save()

  return JsonResponse({'state': 'started'})

