from django.shortcuts import render
from django.views import generic
from django.contrib.auth.views import LoginView, LogoutView
from django.contrib.auth.mixins import LoginRequiredMixin
from django.urls import reverse

from vnsr.settings import DEBUG

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
