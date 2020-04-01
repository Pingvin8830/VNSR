from django.contrib.auth.mixins import LoginRequiredMixin
from django.db.utils import IntegrityError
from django.shortcuts import redirect
from django.urls import reverse_lazy
from django.views import generic
from . import forms, models, tasks

# Create your views here.
class AddDependences(LoginRequiredMixin, generic.TemplateView):
  login_url = reverse_lazy('auth_app:login')
  redirect_field_name = None
  http_method_names = ['get', 'post']
  template_name = 'sheduler/add_dependences.html'

  def get(self, request, *args, **kwargs):
    response = super().get(request, *args, **kwargs)
    response.context_data['details'] = models.Details.objects.filter(is_done=False).filter(dependences=None)
    return response

  def post(self, request, *args, **kwargs):
    response = super().get(request, *args, **kwargs)
    detail = models.Details.objects.get(pk=request.POST['detail'])
    bad_details = [detail]
    is_done = False
    while not is_done:
      is_done = True
      for dependence in models.Dependences.objects.all():
        for bad_detail in bad_details:
          if dependence.prev_detail == bad_detail and dependence.detail not in bad_details:
            bad_details.append(dependence.detail)
            is_done = False
    response.context_data['detail'] = detail
    response.context_data['details'] = []
    for dtl in models.Details.objects.filter(is_done=False).exclude(pk=detail.id):
      if dtl not in bad_details:
        response.context_data['details'].append(dtl)
    is_done = False
    for prev_detail_id in request.POST:
      try:
        prev_detail = models.Details.objects.get(pk=prev_detail_id)
      except ValueError:
        continue
      is_done = True
      dependence = models.Dependences()
      dependence.detail = detail
      dependence.prev_detail = prev_detail
      dependence.save()
    if is_done:
      return redirect(reverse_lazy('sheduler:current_issues'))
    return response

class AddIssues(LoginRequiredMixin, generic.TemplateView):
  login_url = reverse_lazy('auth_app:login')
  redirect_field_name = None
  http_method_names = ['get', 'post']
  template_name = 'sheduler/add_issues.html'

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
        try:
          detail.save()
        except IntegrityError:
          continue
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
    humans = models.Humans.objects.exclude(details=None).filter(details__is_done=False, details__dependences=None)
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
      for dependence in models.Dependences.objects.filter(prev_detail=detail):
        dependence.delete()
    return self.get(request)
