from django.views import generic

# Create your views here.
class Index(generic.TemplateView):
  http_method_names = ['get']
  template_name = 'main/index.html'

