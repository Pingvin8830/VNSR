from django.contrib.auth.mixins import LoginRequiredMixin
from django.views import generic
from django.urls import reverse_lazy

from helper import models
from helper.links import forms

# Create your views here.
class Add(LoginRequiredMixin, generic.CreateView):
  context_object_name = 'link'
  form_class = forms.Add
  http_method_names = ['get', 'post']
  model = models.Links
  success_url = reverse_lazy('helper:links:index')
  template_name = 'helper/links/add.html'

class Index(LoginRequiredMixin, generic.ListView):
  login_url = reverse_lazy('auth_app:login')
  redirect_field_name = None
  context_object_name = 'links'
  http_method_names = ['get']
  model = models.Links
  ordering = ['name']
  template_name = 'helper/links/index.html'

