# -*- coding: utf-8 -*-
# Generated by Django 1.11.2 on 2017-08-27 02:50
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('car_app', '0002_azs'),
    ]

    operations = [
        migrations.CreateModel(
            name='FuelTypes',
            fields=[
                ('id', models.AutoField(primary_key=True, serialize=False)),
                ('name', models.CharField(max_length=30)),
                ('comment', models.CharField(max_length=100)),
            ],
            options={
                'db_table': 'fuel_types',
            },
        ),
    ]
