# Generated by Django 4.2.11 on 2024-03-25 13:51

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('travels', '0016_hotel_address_place_address'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='hotel',
            name='city',
        ),
        migrations.RemoveField(
            model_name='hotel',
            name='name',
        ),
        migrations.RemoveField(
            model_name='place',
            name='city',
        ),
        migrations.RemoveField(
            model_name='place',
            name='name',
        ),
    ]