# Generated by Django 4.2.11 on 2024-05-03 10:06

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('travels', '0023_alter_point_options_remove_way_start_point_and_more'),
    ]

    operations = [
        migrations.AddField(
            model_name='hotel',
            name='name',
            field=models.CharField(default='Unknown', max_length=50, verbose_name='Название'),
            preserve_default=False,
        ),
        migrations.AlterUniqueTogether(
            name='hotel',
            unique_together={('arrival', 'departure')},
        ),
        migrations.RemoveField(
            model_name='hotel',
            name='travel',
        ),
    ]
