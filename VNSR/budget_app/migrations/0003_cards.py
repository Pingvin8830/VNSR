# -*- coding: utf-8 -*-
# Generated by Django 1.11.2 on 2017-08-27 02:15
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('budget_app', '0002_debettypes'),
    ]

    operations = [
        migrations.CreateModel(
            name='Cards',
            fields=[
                ('id', models.AutoField(primary_key=True, serialize=False)),
                ('number', models.CharField(max_length=50, null=True)),
                ('name', models.CharField(max_length=50)),
                ('comment', models.CharField(max_length=100, null=True)),
            ],
            options={
                'db_table': 'cards',
            },
        ),
    ]
