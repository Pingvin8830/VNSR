from django.contrib.auth.mixins import LoginRequiredMixin
from django.urls import reverse_lazy
from django.views import generic
from sheduler import models
from . import forms

# Create your views here.
class Add(LoginRequiredMixin, generic.CreateView):
  login_url = reverse_lazy('auth_app:login')
  redirect_field_name = None
  form_class = forms.Add
  http_method_names = ['get', 'post']
  model = models.Details
  success_url = reverse_lazy('sheduler:details:list')
  template_name = 'sheduler/details/add.html'

class List(LoginRequiredMixin, generic.ListView):
  login_url = reverse_lazy('auth_app:login')
  redirect_field_name = None
  context_object_name = 'details'
  http_method_names = ['get']
  model = models.Details
  ordering = ['human', 'location', 'task']
  template_name = 'sheduler/details/list.html'

class Update(LoginRequiredMixin, generic.UpdateView):
  login_url = reverse_lazy('auth_app:login')
  redirect_field_name = None
  context_object_name = 'detail'
  form_class = forms.Update
  http_method_names = ['get', 'post']
  model = models.Details
  success_url = reverse_lazy('sheduler:details:list')
  template_name = 'sheduler/details/update.html'

