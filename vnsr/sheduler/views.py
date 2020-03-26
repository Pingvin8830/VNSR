from django.urls import reverse_lazy
from django.views import generic
from . import forms, models

# Create your views here.
class Index(generic.TemplateView):
  http_method_names = ['get']
  template_name = 'sheduler/index.html'

  def get_context_data(self, **kwargs):
    context = super().get_context_data(**kwargs)
    details = models.Details.objects.filter(is_done=False)
    context['details'] = []
    for detail in details:
      if not detail.dependences.all():
        context['details'].append(detail)
    return context

