# -*- coding: utf-8 -*-
# Generated by Django 1.11.2 on 2017-12-30 11:36
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('car_app', '0007_checkpoints'),
    ]

    operations = [
        migrations.RenameModel(
            old_name='Azs',
            new_name='Azss',
        ),
        migrations.RenameModel(
            old_name='PayTypes',
            new_name='PayTypesOld',
        ),
        migrations.AlterField(
            model_name='refuels',
            name='pay_type',
            field=models.IntegerField(null=True),
        ),
        migrations.AlterModelTable(
            name='azss',
            table='azss',
        ),
        migrations.AlterModelTable(
            name='paytypesold',
            table='pay_types_old',
        ),
    ]
