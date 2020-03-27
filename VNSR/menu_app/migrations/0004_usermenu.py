# -*- coding: utf-8 -*-
# Generated by Django 1.11.2 on 2017-08-27 03:44
from __future__ import unicode_literals

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('menu_app', '0003_users'),
    ]

    operations = [
        migrations.CreateModel(
            name='UserMenu',
            fields=[
                ('id', models.AutoField(primary_key=True, serialize=False)),
                ('item', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='menu_app.ItemsMenu', verbose_name='Приложение')),
                ('user', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='menu_app.Users', verbose_name='Пользователь')),
            ],
            options={
                'verbose_name': 'Меню пользователя',
                'verbose_name_plural': 'Меню пользователей',
                'db_table': 'user_menu',
            },
        ),
    ]
