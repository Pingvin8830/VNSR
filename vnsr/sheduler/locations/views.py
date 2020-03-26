from django.urls import reverse_lazy
from django.views import generic
from sheduler import models
from . import forms

# Create your views here.
class Add(generic.CreateView):
  form_class = forms.Add
  http_method_names = ['get', 'post']
  model = models.Locations
  success_url = reverse_lazy('sheduler:locations:list')
  template_name = 'sheduler/locations/add.html'

class List(generic.ListView):
  context_object_name = 'locations'
  http_method_names = ['get']
  model = models.Locations
  ordering = ['name']
  template_name = 'sheduler/locations/list.html'

class Update(generic.UpdateView):
  context_object_name = 'location'
  form_class = forms.Update
  http_method_names = ['get', 'post']
  model = models.Locations
  success_url = reverse_lazy('sheduler:locations:list')
  template_name = 'sheduler/locations/update.html'

