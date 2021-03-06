# -*- coding: utf-8 -*-
# Generated by Django 1.11.2 on 2017-08-27 03:04
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('computers_app', '0002_cpus'),
    ]

    operations = [
        migrations.CreateModel(
            name='HDDs',
            fields=[
                ('id', models.AutoField(primary_key=True, serialize=False, verbose_name='Код')),
                ('maker', models.CharField(max_length=20, verbose_name='Изготовитель')),
                ('volume_count', models.PositiveSmallIntegerField(verbose_name='Объём')),
                ('volume_text', models.CharField(max_length=5, verbose_name='Единица измерения объёма')),
                ('interface', models.CharField(max_length=10, verbose_name='Интерфейс')),
                ('time_connect', models.PositiveSmallIntegerField(null=True, verbose_name='Время доступа')),
                ('time_read', models.PositiveSmallIntegerField(null=True, verbose_name='Время чтения/записи')),
                ('time_text', models.CharField(max_length=5, null=True, verbose_name='Единица измерения времени')),
                ('comment', models.CharField(max_length=100, null=True, verbose_name='Комментарий')),
            ],
            options={
                'db_table': 'hdds',
            },
        ),
    ]
