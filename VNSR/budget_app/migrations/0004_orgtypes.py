# -*- coding: utf-8 -*-
# Generated by Django 1.11.2 on 2017-08-27 02:17
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('budget_app', '0003_cards'),
    ]

    operations = [
        migrations.CreateModel(
            name='OrgTypes',
            fields=[
                ('id', models.AutoField(primary_key=True, serialize=False)),
                ('name', models.CharField(max_length=50)),
                ('comment', models.CharField(max_length=100, null=True)),
            ],
            options={
                'db_table': 'organization_types',
            },
        ),
    ]
