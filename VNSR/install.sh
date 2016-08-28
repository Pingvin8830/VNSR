#!/bin/bash

mysql << SQL

	DROP DATABASE IF EXISTS baltbank;
	DROP DATABASE IF EXISTS calend;
	DROP DATABASE IF EXISTS menu;
	DROP DATABASE IF EXISTS vnsr_default;
	DROP DATABASE IF EXISTS metro;

	CREATE DATABASE baltbank;
	CREATE DATABASE calend;
	CREATE DATABASE menu;
	CREATE DATABASE vnsr_default;
	CREATE DATABASE metro;

SQL

rm -r ./baltbank_app/migrations/*
rm -r ./calend_app/migrations/*
rm -r ./menu_app/migrations/*
rm -r ./metro_app/migrations/*

python manage.py migrate --database default
python manage.py createsuperuser

python manage.py makemigrations baltbank_app
python manage.py makemigrations calend_app
python manage.py makemigrations menu_app
python manage.py makemigrations metro_app

python manage.py migrate --database default
python manage.py migrate --database baltbank_db
python manage.py migrate --database calend_db
python manage.py migrate --database menu_db
python manage.py migrate --database metro_db

mysql << SQL

	USE menu;
		INSERT INTO users
			(name) VALUES
			('anton');

		INSERT INTO items_menu
			(app, text) VALUES
			('menu', 'Настройка меню');

		INSERT INTO items_app
			(text, href) VALUES
			('Приложения',        'set_app'),
			('Пользователи',      'set_user'),
			('Действия',          'set_item'),
			('Меню пользователя', 'case_user'),
			('Меню приложений',   'case_app');

		INSERT INTO user_menu
			(item_id, user_id) VALUES
			(1, 1);

		INSERT INTO app_menu
			(app_id, item_id) VALUES
			(1, 1),
			(1, 2),
			(1, 3),
			(1, 4),
			(1, 5);

SQL
