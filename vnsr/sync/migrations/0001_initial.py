# Generated by Django 4.2.11 on 2024-04-21 21:34

from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Objects',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=50)),
                ('field', models.CharField(max_length=50)),
                ('value', models.CharField(max_length=255)),
            ],
            options={
                'verbose_name': 'Объект',
                'verbose_name_plural': 'Объекты',
            },
        ),
    ]
