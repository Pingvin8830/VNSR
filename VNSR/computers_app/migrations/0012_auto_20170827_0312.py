# -*- coding: utf-8 -*-
# Generated by Django 1.11.2 on 2017-08-27 03:12
from __future__ import unicode_literals

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('computers_app', '0011_auto_20170827_0312'),
    ]

    operations = [
        migrations.CreateModel(
            name='StaffNeed',
            fields=[
                ('id', models.AutoField(primary_key=True, serialize=False)),
                ('is_need', models.BooleanField(default=False, verbose_name='Необходимость')),
                ('last_control', models.DateTimeField(null=True, verbose_name='Время последней проверки')),
                ('host', models.ForeignKey(db_column='host', on_delete=django.db.models.deletion.CASCADE, to='computers_app.Hosts', verbose_name='Хост')),
                ('type', models.ForeignKey(db_column='type', on_delete=django.db.models.deletion.CASCADE, to='computers_app.StaffTypes', verbose_name='Тип')),
            ],
            options={
                'db_table': 'staff_need',
            },
        ),
        migrations.AlterUniqueTogether(
            name='staffneed',
            unique_together=set([('host', 'type')]),
        ),
    ]
