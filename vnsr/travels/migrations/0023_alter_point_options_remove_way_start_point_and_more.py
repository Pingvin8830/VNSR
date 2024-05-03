# Generated by Django 4.2.11 on 2024-05-02 18:42

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('kladr', '0007_alter_address_name'),
        ('travels', '0022_alter_point_odometer_alter_point_unique_together'),
    ]

    operations = [
        migrations.AlterModelOptions(
            name='point',
            options={'ordering': ['odometer'], 'verbose_name': 'Путевая точка', 'verbose_name_plural': 'Путевые точки'},
        ),
        migrations.RemoveField(
            model_name='way',
            name='start_point',
        ),
        migrations.RemoveField(
            model_name='way',
            name='target_point',
        ),
        migrations.AddField(
            model_name='way',
            name='start_address',
            field=models.ForeignKey(default=1, on_delete=django.db.models.deletion.PROTECT, related_name='start_address', to='kladr.address', verbose_name='Старт'),
            preserve_default=False,
        ),
        migrations.AddField(
            model_name='way',
            name='target_address',
            field=models.ForeignKey(default=1, on_delete=django.db.models.deletion.PROTECT, related_name='target_address', to='kladr.address', verbose_name='Цель'),
            preserve_default=False,
        ),
    ]
