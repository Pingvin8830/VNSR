from django.urls import reverse_lazy
from django.views import generic
from sheduler import models
from . import forms

# Create your views here.
class Add(generic.CreateView):
  form_class = forms.Add
  http_method_names = ['get', 'post']
  model = models.Dependences
  success_url = reverse_lazy('sheduler:dependences:list')
  template_name = 'sheduler/dependences/add.html'

class List(generic.ListView):
  context_object_name = 'dependences'
  http_method_names = ['get']
  model = models.Dependences
  template_name = 'sheduler/dependences/list.html'

class Update(generic.UpdateView):
  context_object_name = 'dependence'
  form_class = forms.Update
  http_method_names = ['get', 'post']
  model = models.Dependences
  success_url = reverse_lazy('sheduler:dependences:list')
  template_name = 'sheduler/dependences/update.html'

