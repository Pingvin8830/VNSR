from . import models

class Details():
  @staticmethod
  def current_issues():
    return models.Details.objects.filter(is_done=False)

