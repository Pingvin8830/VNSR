# Generated by Django 4.2.11 on 2024-03-25 13:56

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('travels', '0017_remove_hotel_city_remove_hotel_name_and_more'),
    ]

    operations = [
        migrations.AddField(
            model_name='hotel',
            name='name',
            field=models.CharField(default=1, max_length=255, verbose_name='Название'),
            preserve_default=False,
        ),
    ]
