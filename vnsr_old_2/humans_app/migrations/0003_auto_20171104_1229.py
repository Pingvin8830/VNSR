# -*- coding: utf-8 -*-
# Generated by Django 1.11.2 on 2017-11-04 12:29
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('humans_app', '0002_auto_20171104_1227'),
    ]

    operations = [
        migrations.AlterField(
            model_name='humans',
            name='comment',
            field=models.CharField(max_length=250, null=True),
        ),
    ]