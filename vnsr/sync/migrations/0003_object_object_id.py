# Generated by Django 4.2.11 on 2024-04-21 21:51

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('sync', '0002_rename_objects_object'),
    ]

    operations = [
        migrations.AddField(
            model_name='object',
            name='object_id',
            field=models.IntegerField(default=0),
            preserve_default=False,
        ),
    ]