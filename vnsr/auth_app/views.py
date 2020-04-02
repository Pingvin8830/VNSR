from django.contrib.auth import authenticate, login, logout
from django.shortcuts import redirect
from django.urls import reverse_lazy
from django.views import generic
from . import forms

# Create your views here.
class Login(generic.FormView):
  form_class = forms.Login
  http_method_names = ['get', 'post']
  success_url = reverse_lazy('main:index')
  template_name = 'auth_app/login.html'

  def get(self, request, *args, **kwargs):
    if request.user.is_authenticated:
      return redirect(reverse_lazy('main:index'))
    else:
      return super().get(request, *args, **kwargs)

  def post(self, request, *args, **kwargs):
    username = request.POST['login']
    password = request.POST['password']
    user = authenticate(request, username=username, password=password)
    if user is not None:
      login(request, user)
    return redirect(reverse_lazy('main:index'))

class Logout(generic.View):
  http_method_names = []

  def http_method_not_allowed(self, request, *args, **kwargs):
    logout(request)
    return redirect(reverse_lazy('main:index'))
