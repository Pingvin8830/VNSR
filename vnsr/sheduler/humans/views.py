from django.urls import reverse_lazy
from django.views import generic
from sheduler import models
from . import forms

# Create your views here.
class Add(generic.CreateView):
  form_class = forms.Add
  http_method_names = ['get', 'post']
  model = models.Humans
  success_url = reverse_lazy('sheduler:humans:list')
  template_name = 'sheduler/humans/add.html'

class List(generic.ListView):
  context_object_name = 'humans'
  http_method_names = ['get']
  model = models.Humans
  ordering = ['name']
  template_name = 'sheduler/humans/list.html'

class Update(generic.UpdateView):
  context_object_name = 'human'
  form_class = forms.Update
  http_method_names = ['get', 'post']
  model = models.Humans
  success_url = reverse_lazy('sheduler:humans:list')
  template_name = 'sheduler/humans/update.html'

