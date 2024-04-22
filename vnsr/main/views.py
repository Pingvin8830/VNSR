from django.shortcuts import render
from django.views import generic
from django.views.decorators.csrf import csrf_exempt
from django.contrib.auth.views import LoginView, LogoutView
from django.contrib.auth.mixins import LoginRequiredMixin
from django.urls import reverse
from django.http import JsonResponse
from django.middleware.csrf import get_token

from vnsr.settings import DEBUG

import json

# Create your views here.
class Index(LoginRequiredMixin, generic.TemplateView):
  template_name = 'main/index.html'

  def get_context_data(self, **kwargs):
    context = super().get_context_data(**kwargs)
    context['debug'] = DEBUG
    context['active_app'] = 'main'
    return context

class Login(LoginView):
  template_name = 'main/login.html'

  def get_context_data(self, **kwargs):
    context = super().get_context_data(**kwargs)
    context['debug'] = DEBUG
    return context

class Logout(LogoutView):
  next_page = '/'

@csrf_exempt
def sync(request):
  if request.method == 'GET':
    print()
    print(request.headers)
    print(get_token(request))
    print()
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
    return JsonResponse(data=response_json, headers={'CSRF': get_token(request)})
  else:
    request_data = json.loads(request.body.decode('utf8'))
    response_data = {
      'object': request_data['object'],
      'id': request_data['id'],
      'state': 'Ok'
    }
    print()
    print(request_data)
    print()
    return JsonResponse(response_data, headers={'CSRF': get_token(request)})
