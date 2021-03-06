# -*- coding: utf-8 -*-
# Generated by Django 1.11.2 on 2017-08-27 03:10
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('computers_app', '0008_hosts'),
    ]

    operations = [
        migrations.CreateModel(
            name='StaffTypes',
            fields=[
                ('id', models.AutoField(primary_key=True, serialize=False)),
                ('name', models.CharField(max_length=100, unique=True, verbose_name='Название')),
                ('comment', models.CharField(max_length=100, null=True, verbose_name='Комментарий')),
            ],
            options={
                'db_table': 'staff_types',
            },
        ),
    ]
