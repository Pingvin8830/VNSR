from django.urls import reverse_lazy
from django.views import generic
from sheduler import models
from . import forms

# Create your views here.
class Add(generic.CreateView):
  form_class = forms.Add
  http_method_names = ['get', 'post']
  model = models.Tasks
  success_url = reverse_lazy('sheduler:tasks:list')
  template_name = 'sheduler/tasks/add.html'

class List(generic.ListView):
  context_object_name = 'tasks'
  http_method_names = ['get']
  model = models.Tasks
  ordering = ['name']
  template_name = 'sheduler/tasks/list.html'

class Update(generic.UpdateView):
  context_object_name = 'task'
  form_class = forms.Update
  http_method_names = ['get', 'post']
  model = models.Tasks
  success_url = reverse_lazy('sheduler:tasks:list')
  template_name = 'sheduler/tasks/update.html'

