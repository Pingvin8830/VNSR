# Generated by Django 4.2.11 on 2024-03-18 10:03

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('travels', '0005_alter_point_options'),
    ]

    operations = [
        migrations.CreateModel(
            name='Way',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('distance', models.DecimalField(decimal_places=1, max_digits=5)),
                ('start_point', models.ForeignKey(on_delete=django.db.models.deletion.PROTECT, related_name='start_points', to='travels.point', verbose_name='Старт')),
                ('target_point', models.ForeignKey(on_delete=django.db.models.deletion.PROTECT, related_name='target_points', to='travels.point', verbose_name='Цель')),
            ],
            options={
                'verbose_name': 'Отрезок пути',
                'verbose_name_plural': 'Отрезки пути',
            },
        ),
    ]
