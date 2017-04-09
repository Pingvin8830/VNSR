from os                                 import system as bash
from django.shortcuts                   import render, redirect
from django.template.context_processors import csrf
from .models                            import Hosts
from main_app.views                     import is_user, default_context
from menu_app.functions                 import create_menu_app


# Create your views here.

def staff_hosts (request):
  '''Управление хостами'''
  if not is_user (request): return redirect ('/')
  if request.POST:
    hosts = Hosts.objects.all ()
    for id in request.POST:
      try:
        doing = request.POST [id]
        id    = int (id)
        host  = Hosts.objects.get (id = id)
        if   doing == 'on' and host.net.is_wol: bash ('wol %s' % host.net.mac)
        elif doing == 'res': bash ('ssh %s "sudo reboot"'   % host.name)
        elif doing == 'off': bash ('ssh %s "sudo poweroff"' % host.name)
      except:
        continue
    return redirect ('/')
  else:
    page              = 'computers/staff_hosts.html'
    context           = default_context (request)
    context ['hosts'] = Hosts.objects.all ()
    context.update (csrf (request))
  return render (request, page, context)

def index (request):
  '''Стартовое представление приложения'''
  if not is_user (request): return redirect ('/')
  page    = 'computers/index.html'
  context = default_context (request)
  context ['items'] = create_menu_app ('computers')
  return render (request, page, context)

