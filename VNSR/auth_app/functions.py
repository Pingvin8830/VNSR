from django.contrib import auth

def is_user (request):
  '''Проверка авторизации'''
  return auth.get_user (request).username != ''

