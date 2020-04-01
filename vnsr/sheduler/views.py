from django.contrib.auth.mixins import LoginRequiredMixin
from django.shortcuts import redirect
from django.urls import reverse_lazy
from django.views import generic
from . import forms, models, tasks

# Create your views here.
class AddIssues(LoginRequiredMixin, generic.TemplateView):
  login_url = reverse_lazy('auth_app:login')
  redirect_field_name = None
  http_method_names = ['get', 'post']
  template_name = 'sheduler/add.html'

  def get(self, request, *args, **kwargs):
    response = super().get(request, *args, **kwargs)
    response.context_data['type'] = 'task'
    response.context_data['objects'] = models.Tasks.objects.all()
    response.context_data['multi'] = False
    return response

  def post(self, request, *args, **kwargs):
    response = super().get(request, *args, **kwargs)
    response.context_data['task'] = models.Tasks.objects.get(pk=request.POST['task'])
    response.context_data['locations_ids'] = request.POST['locations_ids']
    response.context_data['locations'] = []
    response.context_data['humans_ids'] = request.POST['humans_ids']
    response.context_data['humans'] = []
    response.context_data['multi'] = True
    response.context_data['type'] = 'locations'
    response.context_data['objects'] = models.Locations.objects.all()
    if not response.context_data['locations_ids']:
      for location_id in request.POST:
        try:
          location = models.Locations.objects.get(pk=location_id)
          response.context_data['locations_ids'] += location_id + ' '
        except ValueError:
          continue
      if response.context_data['locations_ids']:
        response.context_data['type'] = 'humans'
        response.context_data['objects'] = models.Humans.objects.all()
    elif not response.context_data['humans_ids']:
      for human_id in request.POST:
        try:
          human = models.Humans.objects.get(pk=human_id)
          response.context_data['humans_ids'] += human_id + ' '
        except ValueError:
          continue
      if response.context_data['humans_ids']:
        response.context_data['type'] = 'excess'
        response.context_data['objects'] = []
        for location_id in response.context_data['locations_ids'].split():
          location = models.Locations.objects.get(pk=location_id)
          response.context_data['locations'].append(location)
        for human_id in response.context_data['humans_ids'].split():
          human = models.Humans.objects.get(pk=human_id)
          response.context_data['humans'].append(human)
    else:
      for key in request.POST:
        try:
          (location_id, human_id) = key.split('_')
          location = models.Locations.objects.get(pk=location_id)
          human = models.Humans.objects.get(pk=human_id)
        except ValueError:
          continue
        detail = models.Details()
        detail.task = response.context_data['task']
        detail.location = location
        detail.human = human
        detail.save()
      return redirect(reverse_lazy('sheduler:current_issues'))
    return response

class CurrentIssues(LoginRequiredMixin, generic.TemplateView):
  login_url = reverse_lazy('auth_app:login')
  redirect_field_name = None
  http_method_names = ['get', 'post']
  template_name = 'sheduler/current_issues.html'

  def get_context_data(self, **kwargs):
    context = super().get_context_data(**kwargs)
    context['humans'] = []
    humans = models.Humans.objects.exclude(details=None).filter(details__is_done=False)
    humans_list = []
    for human in humans:
      for detail in human.details_set.all():
        if human not in humans_list:
          humans_list.append(human)
          human_dict = {'name': human.name, 'rowspan': 0, 'locations': []}
          locations = models.Locations.objects.exclude(details=None).filter(details__human=human, details__is_done=False, details__dependences=None)
          locations_list = []
          for location in locations:
            if location not in locations_list:
              locations_list.append(location)
              details = models.Details.objects.filter(human=human).filter(location=location).filter(is_done=False).filter(dependences=None)
              add_rows = details.count()
              location_dict = {'name': location.name, 'rowspan': add_rows, 'details': details}
              human_dict['rowspan'] += 1 + add_rows
              human_dict['locations'].append(location_dict)
          context['humans'].append(human_dict)
    return context

  def post(self, request, *args, **kwargs):
    for detail_id in request.POST:
      try:
        detail = models.Details.objects.get(pk=detail_id)
      except ValueError:
        continue
      detail.is_done = True
      detail.save()
    return self.get(request)
