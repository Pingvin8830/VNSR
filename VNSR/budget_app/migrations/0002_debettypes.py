# -*- coding: utf-8 -*-
# Generated by Django 1.11.2 on 2017-08-27 02:14
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
        ('budget_app', '0001_initial'),
    ]

    operations = [
        migrations.CreateModel(
            name='DebetTypes',
            fields=[
                ('id', models.AutoField(primary_key=True, serialize=False)),
                ('name', models.CharField(max_length=50)),
                ('comment', models.CharField(max_length=100, null=True)),
            ],
            options={
                'db_table': 'debet_types',
            },
        ),
    ]
