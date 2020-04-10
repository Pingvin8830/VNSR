from django.contrib.auth.mixins import LoginRequiredMixin
from django.views import generic
from django.urls import reverse_lazy

from . import models

# Create your views here.
class Index(LoginRequiredMixin, generic.TemplateView):
  login_url = reverse_lazy('auth_app:login')
  redirect_field_name = None
  http_method_names = ['get']
  template_name = 'helper/index.html'

