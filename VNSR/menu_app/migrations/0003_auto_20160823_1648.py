# -*- coding: utf-8 -*-
# Generated by Django 1.9.9 on 2016-08-23 13:48
from __future__ import unicode_literals

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('menu_app', '0002_auto_20160823_1643'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='itemsapp',
            name='after',
        ),
        migrations.RemoveField(
            model_name='itemsapp',
            name='before',
        ),
    ]
