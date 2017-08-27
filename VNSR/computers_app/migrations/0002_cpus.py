# -*- coding: utf-8 -*-
# Generated by Django 1.11.2 on 2017-08-27 03:03
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
        ('computers_app', '0001_initial'),
    ]

    operations = [
        migrations.CreateModel(
            name='CPUs',
            fields=[
                ('id', models.AutoField(primary_key=True, serialize=False, verbose_name='Код')),
                ('name', models.CharField(max_length=30, verbose_name='Название')),
                ('frequency_count', models.PositiveSmallIntegerField(verbose_name='Частота процессора')),
                ('frequency_text', models.CharField(max_length=5, verbose_name='Единица измерения частоты процессора')),
                ('frequency_bus_count', models.PositiveSmallIntegerField(null=True, verbose_name='Частота системной шины')),
                ('frequency_bus_text', models.CharField(max_length=5, null=True, verbose_name='Единица измерения частоты системной шины')),
                ('bit', models.PositiveSmallIntegerField(verbose_name='Разрядность')),
                ('count', models.PositiveSmallIntegerField(verbose_name='Количество ядер')),
                ('tech', models.CharField(max_length=10, null=True, verbose_name='Техпроцесс')),
                ('socket', models.CharField(max_length=10, verbose_name='Сокет')),
                ('cache_count', models.PositiveSmallIntegerField(null=True, verbose_name='Размер кэша')),
                ('cache_text', models.CharField(max_length=5, null=True, verbose_name='Единица измерения объёма кэша')),
                ('temp_max', models.DecimalField(decimal_places=2, max_digits=5, null=True, verbose_name='Максимальная рабочая температура')),
                ('comment', models.CharField(max_length=100, null=True, verbose_name='Комментарий')),
            ],
            options={
                'db_table': 'cpus',
            },
        ),
    ]