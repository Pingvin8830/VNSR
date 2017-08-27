# -*- coding: utf-8 -*-
# Generated by Django 1.11.2 on 2017-08-27 03:45
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('menu_app', '0004_usermenu'),
    ]

    operations = [
        migrations.CreateModel(
            name='ItemsApp',
            fields=[
                ('id', models.AutoField(primary_key=True, serialize=False)),
                ('text', models.CharField(max_length=20, verbose_name='Название подпункта')),
                ('href', models.CharField(max_length=20, verbose_name='Вторая часть url')),
                ('comment', models.CharField(max_length=100, null=True, verbose_name='Комментарий')),
            ],
            options={
                'verbose_name': 'Подпункт меню',
                'verbose_name_plural': 'Подпункты меню',
                'db_table': 'items_app',
            },
        ),
    ]
