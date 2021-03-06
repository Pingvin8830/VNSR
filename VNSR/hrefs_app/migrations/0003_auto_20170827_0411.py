# -*- coding: utf-8 -*-
# Generated by Django 1.11.2 on 2017-08-27 04:11
from __future__ import unicode_literals

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('hrefs_app', '0002_links'),
    ]

    operations = [
        migrations.CreateModel(
            name='UserLinks',
            fields=[
                ('id', models.AutoField(primary_key=True, serialize=False)),
                ('user', models.IntegerField()),
                ('link', models.ForeignKey(db_column='link', default=0, on_delete=django.db.models.deletion.SET_DEFAULT, to='hrefs_app.Links')),
            ],
            options={
                'db_table': 'user_links',
            },
        ),
        migrations.AlterUniqueTogether(
            name='userlinks',
            unique_together=set([('user', 'link')]),
        ),
    ]
