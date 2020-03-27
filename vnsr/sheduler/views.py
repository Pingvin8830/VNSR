from django.contrib.auth.mixins import LoginRequiredMixin
from django.urls import reverse_lazy
from django.views import generic
from . import forms, models

# Create your views here.
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
