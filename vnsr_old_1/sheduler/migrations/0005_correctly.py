# Generated by Django 3.0.4 on 2020-03-26 12:42

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('sheduler', '0004_create_details'),
    ]

    operations = [
        migrations.AlterModelOptions(
            name='details',
            options={'ordering': ['human__name', 'location__name', 'task__name'], 'verbose_name': 'Деталь', 'verbose_name_plural': 'Детали'},
        ),
        migrations.AlterField(
            model_name='details',
            name='human',
            field=models.ForeignKey(null=True, on_delete=django.db.models.deletion.SET_NULL, to='sheduler.Humans', verbose_name='Человек'),
        ),
        migrations.AlterField(
            model_name='details',
            name='location',
            field=models.ForeignKey(null=True, on_delete=django.db.models.deletion.SET_NULL, to='sheduler.Locations', verbose_name='Место'),
        ),
        migrations.AlterField(
            model_name='details',
            name='task',
            field=models.ForeignKey(null=True, on_delete=django.db.models.deletion.SET_NULL, to='sheduler.Tasks', verbose_name='Задача'),
        ),
        migrations.AlterField(
            model_name='humans',
            name='name',
            field=models.CharField(max_length=50, unique=True, verbose_name='Имя'),
        ),
        migrations.AlterField(
            model_name='locations',
            name='name',
            field=models.CharField(max_length=50, unique=True, verbose_name='Название'),
        ),
    ]