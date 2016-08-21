# -*- coding: utf-8 -*-
# Generated by Django 1.9.8 on 2016-08-16 19:11
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Signs',
            fields=[
                ('data', models.DateField(primary_key=True, serialize=False)),
                ('work', models.BooleanField(default=True)),
                ('week', models.BooleanField(default=False)),
                ('holiday', models.BooleanField(default=False)),
                ('short', models.BooleanField(default=False)),
                ('comment', models.CharField(max_length=100)),
            ],
            options={
                'db_table': 'signs',
            },
        ),
    ]