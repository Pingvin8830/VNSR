# -*- coding: utf-8 -*-
# Generated by Django 1.11.2 on 2017-08-27 02:17
from __future__ import unicode_literals

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('budget_app', '0004_orgtypes'),
    ]

    operations = [
        migrations.CreateModel(
            name='Orgs',
            fields=[
                ('id', models.AutoField(primary_key=True, serialize=False)),
                ('name', models.CharField(max_length=100)),
                ('region', models.CharField(default='СПб', max_length=100, null=True)),
                ('city', models.CharField(max_length=50, null=True)),
                ('street', models.CharField(max_length=50, null=True)),
                ('house', models.PositiveIntegerField(null=True)),
                ('build', models.CharField(max_length=2, null=True)),
                ('flat', models.PositiveIntegerField(null=True)),
                ('phone', models.CharField(max_length=50, null=True)),
                ('comment', models.CharField(max_length=100, null=True)),
                ('type', models.ForeignKey(db_column='type', default=-1, on_delete=django.db.models.deletion.SET_DEFAULT, to='budget_app.OrgTypes')),
            ],
            options={
                'db_table': 'organizations',
            },
        ),
    ]