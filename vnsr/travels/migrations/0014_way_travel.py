# Generated by Django 4.2.11 on 2024-03-18 13:27

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('travels', '0013_alter_travel_options_alter_travel_unique_together_and_more'),
    ]

    operations = [
        migrations.AddField(
            model_name='way',
            name='travel',
            field=models.ForeignKey(default=1, on_delete=django.db.models.deletion.PROTECT, related_name='ways', to='travels.travel', verbose_name='Расстояние'),
            preserve_default=False,
        ),
    ]
