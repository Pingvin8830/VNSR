from django.views import generic
from . import selects

# Create your views here.
class CurrentIssues(generic.TemplateView):
  extra_context = selects.Details.current_issues()
  http_method_names = ['get']
  template_name = 'tasks/current_issues.html'
  
class Index(generic.TemplateView):
  http_method_names = ['get']
  template_name = 'tasks/index.html'

