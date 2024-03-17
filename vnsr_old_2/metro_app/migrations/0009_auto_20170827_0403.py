# -*- coding: utf-8 -*-
# Generated by Django 1.11.2 on 2017-08-27 04:03
from __future__ import unicode_literals

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('metro_app', '0008_payslip'),
    ]

    operations = [
        migrations.CreateModel(
            name='PayslipDetails',
            fields=[
                ('id', models.AutoField(primary_key=True, serialize=False)),
                ('period', models.DateField()),
                ('summa', models.DecimalField(decimal_places=4, max_digits=9, null=True)),
                ('count', models.DecimalField(decimal_places=4, max_digits=9, null=True)),
                ('code', models.ForeignKey(db_column='code', null=True, on_delete=django.db.models.deletion.SET_NULL, to='metro_app.CodesPayslip')),
                ('payslip', models.ForeignKey(db_column='payslip', null=True, on_delete=django.db.models.deletion.SET_NULL, to='metro_app.Payslip')),
            ],
            options={
                'db_table': 'payslip_details',
            },
        ),
        migrations.AlterUniqueTogether(
            name='payslipdetails',
            unique_together=set([('payslip', 'code', 'period')]),
        ),
    ]