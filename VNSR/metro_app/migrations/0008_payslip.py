# -*- coding: utf-8 -*-
# Generated by Django 1.11.2 on 2017-08-27 04:02
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('metro_app', '0007_codespayslip'),
    ]

    operations = [
        migrations.CreateModel(
            name='Payslip',
            fields=[
                ('id', models.AutoField(primary_key=True, serialize=False)),
                ('period', models.DateField(unique=True)),
                ('division', models.CharField(default='Оптовая продажа алкоголя', max_length=50)),
                ('post', models.CharField(default='Кассир-оператор ПК', max_length=50)),
                ('rate', models.DecimalField(decimal_places=4, max_digits=9)),
                ('begin_dolg', models.DecimalField(decimal_places=4, default=0, max_digits=9)),
                ('rotate_dolg', models.DecimalField(decimal_places=4, max_digits=9, null=True)),
                ('end_dolg', models.DecimalField(decimal_places=4, default=0, max_digits=9)),
                ('income', models.DecimalField(decimal_places=4, max_digits=9)),
                ('consumption', models.DecimalField(decimal_places=4, max_digits=9)),
                ('paying', models.DecimalField(decimal_places=4, max_digits=9)),
            ],
            options={
                'db_table': 'payslip',
            },
        ),
    ]
