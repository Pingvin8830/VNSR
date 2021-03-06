# -*- coding: utf-8 -*-
# Generated by Django 1.11.2 on 2017-08-27 03:42
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
        ('menu_app', '0001_initial'),
    ]

    operations = [
        migrations.CreateModel(
            name='ItemsMenu',
            fields=[
                ('id', models.AutoField(primary_key=True, serialize=False)),
                ('app', models.CharField(max_length=100, verbose_name='Ссылка')),
                ('text', models.CharField(max_length=100, verbose_name='Подпись')),
                ('comment', models.CharField(max_length=100, null=True, verbose_name='Комментарий')),
            ],
            options={
                'verbose_name': 'Приложение',
                'verbose_name_plural': 'Приложения',
                'db_table': 'items_menu',
            },
        ),
    ]
