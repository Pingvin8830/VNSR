from django.db import models

# Create your models here.
class Links(models.Model):
  class Meta:
    ordering = ['name']
    verbose_name = 'Ссылка'
    verbose_name_plural = 'Ссылки'

  url = models.URLField(unique=True)
  name = models.CharField(max_length=50, unique=True, verbose_name='Название')

