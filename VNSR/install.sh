#!/bin/bash

mysql << SQL

	DROP DATABASE IF EXISTS test_baltbank;
	DROP DATABASE IF EXISTS test_calend;
	DROP DATABASE IF EXISTS test_menu;
	DROP DATABASE IF EXISTS test_vnsr_default;

	CREATE DATABASE test_baltbank;
	CREATE DATABASE test_calend;
	CREATE DATABASE test_menu;
	CREATE DATABASE test_vnsr_default;

SQL

rm -r ./baltbank_app/migrations/*
rm -r ./calend_app/migrations/*
rm -r ./menu_app/migrations/*

python manage.py migrate --database default
python manage.py createsuperuser

python manage.py makemigrations baltbank_app
python manage.py makemigrations calend_app
python manage.py makemigrations menu_app

python manage.py migrate --database default
python manage.py migrate --database baltbank_db
python manage.py migrate --database calend_db
python manage.py migrate --database menu_db

mysql << SQL

	USE test_menu;
		INSERT INTO users
			(name) VALUES
			('anton');

		INSERT INTO items_menu
			(app, text) VALUES
			('menu', 'Настройка меню');

		INSERT INTO items_app
			(text, href) VALUES
			('Действия',        'set_item'),
			('Меню приложений', 'case_app');

		INSERT INTO user_menu
			(item_id, user_id) VALUES
			(1, 1);

		INSERT INTO app_menu
			(app_id, item_id) VALUES
			(1, 1),
			(1, 2);

SQL
