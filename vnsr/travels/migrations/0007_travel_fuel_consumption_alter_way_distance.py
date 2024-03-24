# Generated by Django 4.2.11 on 2024-03-18 10:13

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('travels', '0006_way'),
    ]

    operations = [
        migrations.AddField(
            model_name='travel',
            name='fuel_consumption',
            field=models.DecimalField(decimal_places=1, default=10, max_digits=3, verbose_name='Расход топлива'),
        ),
        migrations.AlterField(
            model_name='way',
            name='distance',
            field=models.DecimalField(decimal_places=1, max_digits=5, verbose_name='Расстояние'),
        ),
    ]
