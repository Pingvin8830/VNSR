# Generated by Django 4.2.11 on 2024-04-27 10:53

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('kladr', '0006_address_name'),
    ]

    operations = [
        migrations.AlterField(
            model_name='address',
            name='name',
            field=models.CharField(max_length=20, unique=True, verbose_name='Название'),
        ),
    ]
