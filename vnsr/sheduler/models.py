from django.db import models

# Create your models here.
class Details(models.Model):
  class Meta:
    ordering = ['human__name', 'location__name', 'task__name']
    unique_together = ['human', 'location', 'task']
    verbose_name = 'Деталь'
    verbose_name_plural = 'Детали'

  human = models.ForeignKey('Humans', on_delete=models.SET_NULL, null=True, verbose_name='Человек')
  location = models.ForeignKey('Locations', on_delete=models.SET_NULL, null=True, verbose_name='Место')
  task = models.ForeignKey('Tasks', on_delete=models.SET_NULL, null=True, verbose_name='Задача')
  is_done = models.BooleanField(db_index=True, default=False)

class Humans(models.Model):
  class Meta:
    ordering = ['name']
    verbose_name = 'Человек'
    verbose_name_plural = 'Люди'

  name = models.CharField(max_length=50, unique=True, verbose_name='Имя')

  def __str__(self):
    return self.name

class Locations(models.Model):
  class Meta:
    ordering = ['name']
    verbose_name = 'Место'
    verbose_name_plural = 'Места'

  name = models.CharField(max_length=50, unique=True, verbose_name='Название')

  def __str__(self):
    return self.name
class Tasks(models.Model):
  class Meta:
    ordering = ['name']
    verbose_name = 'Задача'
    verbose_name_plural = 'Задачи'

  name = models.CharField(max_length=50, unique=True, verbose_name='Название')

  def __str__(self):
    return self.name
