# -*- coding: utf-8 -*-
# Generated by Django 1.11.2 on 2017-08-27 02:54
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('car_app', '0006_travels'),
    ]

    operations = [
        migrations.CreateModel(
            name='CheckPoints',
            fields=[
                ('id', models.AutoField(primary_key=True, serialize=False)),
                ('name', models.CharField(max_length=100)),
                ('date_time_in', models.DateTimeField(null=True)),
                ('date_time_out', models.DateTimeField(null=True)),
                ('odometer', models.PositiveIntegerField()),
                ('comment', models.CharField(max_length=100, null=True)),
                ('travel', models.ForeignKey(db_column='travel', on_delete=models.SET(-1), to='car_app.Travels')),
            ],
            options={
                'db_table': 'check_points',
            },
        ),
    ]
