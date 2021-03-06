# -*- coding: utf-8 -*-
# Generated by Django 1.11.2 on 2017-08-27 03:09
from __future__ import unicode_literals

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('computers_app', '0007_videos'),
    ]

    operations = [
        migrations.CreateModel(
            name='Hosts',
            fields=[
                ('id', models.AutoField(primary_key=True, serialize=False, verbose_name='Код')),
                ('name', models.CharField(max_length=30, verbose_name='Сетевое имя')),
                ('ip', models.CharField(max_length=15, verbose_name='IP-адрес')),
                ('comment', models.CharField(max_length=100, null=True, verbose_name='Комментарий')),
                ('cpu', models.ForeignKey(db_column='cpu', null=True, on_delete=django.db.models.deletion.SET_NULL, to='computers_app.CPUs', verbose_name='Процессор')),
                ('hdd1', models.ForeignKey(db_column='hdd1', null=True, on_delete=django.db.models.deletion.SET_NULL, related_name='hdd1', to='computers_app.HDDs', verbose_name='Винчестер1')),
                ('hdd2', models.ForeignKey(db_column='hdd2', null=True, on_delete=django.db.models.deletion.SET_NULL, related_name='hdd2', to='computers_app.HDDs', verbose_name='Винчестер2')),
                ('hdd3', models.ForeignKey(db_column='hdd3', null=True, on_delete=django.db.models.deletion.SET_NULL, related_name='hdd3', to='computers_app.HDDs', verbose_name='Винчестер3')),
                ('hdd4', models.ForeignKey(db_column='hdd4', null=True, on_delete=django.db.models.deletion.SET_NULL, related_name='hdd4', to='computers_app.HDDs', verbose_name='Винчестер4')),
                ('mother', models.ForeignKey(db_column='mother', null=True, on_delete=django.db.models.deletion.SET_NULL, to='computers_app.Mothers', verbose_name='Материнская плата')),
                ('net', models.ForeignKey(db_column='net', null=True, on_delete=django.db.models.deletion.SET_NULL, to='computers_app.Networks', verbose_name='Сетевая карта')),
                ('ram1', models.ForeignKey(db_column='ram1', null=True, on_delete=django.db.models.deletion.SET_NULL, related_name='ram1', to='computers_app.RAMs', verbose_name='ОП1')),
                ('ram2', models.ForeignKey(db_column='ram2', null=True, on_delete=django.db.models.deletion.SET_NULL, related_name='ram2', to='computers_app.RAMs', verbose_name='ОП2')),
                ('ram3', models.ForeignKey(db_column='ram3', null=True, on_delete=django.db.models.deletion.SET_NULL, related_name='ram3', to='computers_app.RAMs', verbose_name='ОП3')),
                ('ram4', models.ForeignKey(db_column='ram4', null=True, on_delete=django.db.models.deletion.SET_NULL, related_name='ram4', to='computers_app.RAMs', verbose_name='ОП4')),
                ('video', models.ForeignKey(db_column='video', null=True, on_delete=django.db.models.deletion.SET_NULL, to='computers_app.Videos', verbose_name='Видеокарта')),
            ],
            options={
                'db_table': 'hosts',
            },
        ),
    ]
