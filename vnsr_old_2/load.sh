#!/bin/bash

[ -d databases ] && rm -r databases
mkdir databases

cp ./*.7z ./databases

#python manage.py migrate --database default
#python manage.py migrate --database baltbank_db
#python manage.py migrate --database budget_db
#python manage.py migrate --database calend_db
#python manage.py migrate --database car_db
#python manage.py migrate --database computers_db
#python manage.py migrate --database hrefs_db
#python manage.py migrate --database menu_db
#python manage.py migrate --database metro_db

cd databases
7z x *.7z
cd ..

python manage.py migrate

echo
echo '-----'
pwd
ls -laF databases/databases
echo '-----'

for db in `cat databases/databases/databases.list | grep -v -E "(schema|mysql|test|vnsr)"`; do
  echo $db
  python manage.py migrate --database ${db}_db
  cd databases
  echo
  for tb in `cat databases/$db/tables.list | grep -v django`; do
    echo $tb
    up=`grep -v "^+-" databases/$db/$tb.unl | sed "s/[ ]*|/|/g; s/|[ ]*/|/g; s/^|//; s/|$//" | head -1`
    grep -v "^+-" databases/$db/$tb.unl | sed "s/[ ]*|/|/g; s/|[ ]*/|/g; s/^|//; s/|$//" | grep -v "$up" > databases/$db/$tb.load
    echo ".import databases/$db/$tb.load $tb" | sqlite3 $db
    echo '+++'
    done
  cd ..
  echo '-----'
  done

