#!/bin/bash

mysql -u pingvin -pserver881130 << SQL

	DROP DATABASE IF EXISTS test_baltbank;
	DROP DATABASE IF EXISTS test_calend;
	DROP DATABASE IF EXISTS test_menu;
	DROP DATABASE IF EXISTS test_vnsr_default;
	DROP DATABASE IF EXISTS test_metro;

	CREATE DATABASE test_baltbank;
	CREATE DATABASE test_calend;
	CREATE DATABASE test_menu;
	CREATE DATABASE test_vnsr_default;
	CREATE DATABASE test_metro;

SQL

clear

rm -r ./baltbank_app/migrations/*
rm -r ./calend_app/migrations/*
rm -r ./menu_app/migrations/*
rm -r ./metro_app/migrations/*

python manage.py migrate --database default
echo '
	Сейчас необходимо создать административную учётную запись.

	Нажми ENTER и ответь на вопросы
'
read
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

mysql -u pingvin -pserver881130 << SQL

	USE test_menu;
		INSERT INTO users
			(name) VALUES
			('test_admin');

		INSERT INTO items_menu
			(app, text) VALUES
			('menu',   'Настройка меню'),
			('calend', 'Календарь'),
			('metro',  'Metro Cash and Carry');

		INSERT INTO items_app
			(text, href) VALUES
			('Приложения',        'set_app'),
			('Пользователи',      'set_user'),
			('Действия',          'set_item'),
			('Меню пользователя', 'case_user'),
			('Меню приложений',   'case_app'),
			('01 Январь',         '1'),
			('02 Февраль',        '2'),
			('03 Март',           '3'),
			('04 Апрель',         '4'),
			('05 Май',            '5'),
			('06 Июнь',           '6'),
			('07 Июль',           '7'),
			('08 Август',         '8'),
			('09 Сентябрь',       '9'),
			('10 Октябрь',        '10'),
			('11 Ноябрь',         '11'),
			('12 Декабрь',        '12'),
			('Новая смена',       'set_shift'),
			('Новый график',      'set_work_plane');

		INSERT INTO user_menu
			(item_id, user_id) VALUES
			(1, 1),
			(2, 1),
			(3, 1);

		INSERT INTO app_menu
			(app_id, item_id) VALUES
			(1, 1),
			(1, 2),
			(1, 3),
			(1, 4),
			(1, 5),
			(2, 6),
			(2, 7),
			(2, 8),
			(2, 9),
			(2, 10),
			(2, 11),
			(2, 12),
			(2, 13),
			(2, 14),
			(2, 15),
			(2, 16),
			(2, 17),
			(3, 18),
			(3, 19);

SQL

clear
echo '
	!!!ВНИМАНИЕ!!!

	После нажатия на клавишу ENTER запустится сервер django, и откроется административная страница сайта.

	Необходимо зайти созданным администратором и создать новую учётную запись test_admin.
	Далее нужно выйти из интерфейса администратора и закрыть браузер.

	После этого повторно откроется браузер уже на страницу авторизации самого сайта.
	Заходим учеткой test_admin.

'
read

#{
#	sleep 10
#	firefox 127.0.0.1:8000/admin 
#	firefox 127.0.0.1:8000
#} &

sudo systemctl stop httpd
sudo python manage.py runserver 192.168.1.51:80
sudo systemctl start httpd
