# -*- coding: utf-8 -*-
# Generated by Django 1.11.2 on 2017-08-27 02:53
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('car_app', '0005_refuels'),
    ]

    operations = [
        migrations.CreateModel(
            name='Travels',
            fields=[
                ('id', models.AutoField(primary_key=True, serialize=False)),
                ('point_start', models.CharField(max_length=100)),
                ('point_end', models.CharField(max_length=100)),
                ('date_time_start', models.DateTimeField()),
                ('date_time_end', models.DateTimeField()),
                ('distance', models.PositiveIntegerField()),
                ('comment', models.CharField(max_length=100, null=True)),
            ],
            options={
                'db_table': 'travels',
            },
        ),
    ]
