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
  model = models.Tasks
  success_url = reverse_lazy('sheduler:add_issues')
  template_name = 'sheduler/tasks/add.html'

class List(LoginRequiredMixin, generic.ListView):
  login_url = reverse_lazy('auth_app:login')
  redirect_field_name = None
  context_object_name = 'tasks'
  http_method_names = ['get']
  model = models.Tasks
  ordering = ['name']
  template_name = 'sheduler/tasks/list.html'

class Update(LoginRequiredMixin, generic.UpdateView):
  login_url = reverse_lazy('auth_app:login')
  redirect_field_name = None
  context_object_name = 'task'
  form_class = forms.Update
  http_method_names = ['get', 'post']
  model = models.Tasks
  success_url = reverse_lazy('sheduler:tasks:list')
  template_name = 'sheduler/tasks/update.html'

