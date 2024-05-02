# Generated by Django 4.2.11 on 2024-05-02 18:30

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('travels', '0021_alter_point_options_remove_point_datetime_and_more'),
    ]

    operations = [
        migrations.AlterField(
            model_name='point',
            name='odometer',
            field=models.DecimalField(decimal_places=2, max_digits=10),
        ),
        migrations.AlterUniqueTogether(
            name='point',
            unique_together={('arrival_datetime', 'departure_datetime')},
        ),
    ]
