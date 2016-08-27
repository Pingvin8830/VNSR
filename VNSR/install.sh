#!/bin/bash

pwd

exit

mysql << SQL

	DROP DATABASE IF EXISTS baltbank;
	DROP DATABASE IF EXISTS calend;
	DROP DATABASE IF EXISTS menu;
	DROP DATABASE IF EXISTS vnsr_default;

	CREATE DATABASE baltbank;
	CREATE DATABASE calend;
	CREATE DATABASE menu;
	CREATE DATABASE vnsr_default;

SQL

rm -r ./baltbank_app/migrations/*
rm -r ./calend_app/migrations/*
rm -r ./menu_app/migrations/*

python manage.py createsuperuser

python manage.py createmigrations baltbank_app
python manage.py createmigrations calend_app
python manage.py createmigrations menu_app

python manage.py migrate --database default
python manage.py migrate --database baltbank_db
python manage.py migrate --database calend_db
python manage.py migrate --database menu_db

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
			('Действия', 'set_item');

		INSERT INTO user_menu
			(item_id, user_id) VALUES
			(1, 1);

		INSERT INTO app_menu
			(app_id, item_id) VALUES
			(1, 1);

SQL
