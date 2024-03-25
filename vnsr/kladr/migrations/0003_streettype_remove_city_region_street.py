# Generated by Django 4.2.11 on 2024-03-25 11:49

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('kladr', '0002_citytype_alter_region_options_city'),
    ]

    operations = [
        migrations.CreateModel(
            name='StreetType',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=20, verbose_name='Название')),
                ('short', models.CharField(max_length=10, verbose_name='Сокращённое название')),
            ],
            options={
                'verbose_name': 'Тип улицы',
                'verbose_name_plural': 'Типы улиц',
            },
        ),
        migrations.RemoveField(
            model_name='city',
            name='region',
        ),
        migrations.CreateModel(
            name='Street',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=25, verbose_name='Название')),
                ('type', models.ForeignKey(on_delete=django.db.models.deletion.PROTECT, related_name='streets', to='kladr.streettype', verbose_name='Тип')),
            ],
            options={
                'verbose_name': 'Улица',
                'verbose_name_plural': 'Улицы',
            },
        ),
    ]
