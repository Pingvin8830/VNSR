# -*- coding: utf-8 -*-
# Generated by Django 1.11.2 on 2017-08-27 03:12
from __future__ import unicode_literals

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('computers_app', '0010_stafflog'),
    ]

    operations = [
        migrations.CreateModel(
            name='StaffPeriods',
            fields=[
                ('id', models.AutoField(primary_key=True, serialize=False)),
                ('period', models.IntegerField(verbose_name='Период обслуживания')),
                ('host', models.ForeignKey(db_column='host', null=True, on_delete=django.db.models.deletion.SET_NULL, to='computers_app.Hosts', verbose_name='Хост')),
                ('type', models.ForeignKey(db_column='type', null=True, on_delete=django.db.models.deletion.SET_NULL, to='computers_app.StaffTypes', verbose_name='Тип обслуживания')),
            ],
            options={
                'db_table': 'staff_periods',
            },
        ),
        migrations.AlterUniqueTogether(
            name='staffperiods',
            unique_together=set([('host', 'type')]),
        ),
    ]