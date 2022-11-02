## Block 1

 1. Развернуть локальный Hive в любой конфигурации - 20 баллов

**RESULT:
Deployed from https://github.com/tech4242/docker-hadoop-hive-parquet

2) Подключиться к развернутому Hive с помощью любого инструмента: Hue, Python
Driver, Zeppelin, любая IDE итд (15 баллов за любой инструмент, максимум 30
баллов)

3) Сделать скриншоты поднятого Hive и подключений в выбранными вами
инструментах, добавить в репозиторий

**RESULT:
Connected through command prompt, Hue and Python (screenshots connection_*.png)

## Block 2

1) Сделать таблицу artists в Hive и вставить туда значения, используя датасет
https://www.kaggle.com/pieca111/music-artists-popularity - 15 баллов

**RESULT:
connecting to docker:
$ sudo docker exec -it docker-hadoop-hive-parquet_hive-server_1 /bin/bash

starting hive:
#hive

CREATE TABLE artists (mbid string, artist_mb string, artist_lastfm string, country_mb string, country_lastfm string, tags_mb string, tags_lastfm string, listeners_lastfm  int,scrobbles_lastfm int, ambiguous_artist boolean)  ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' TBLPROPERTIES ("skip.header.line.count" = "1");

**RESULT:
the result of the command in file hive_create_table.png

**RESULT:
 LOAD DATA LOCAL INPATH '/home/yuku/made3/3_dl/hw2/artists.csv' OVERWRITE INTO TABLE artists;
the result of the command in file load_artists_to_hive2.png

2) Используя Hive найти (команды и результаты записать в файл и добавить в
репозиторий):

a) Исполнителя с максимальным числом скробблов - 5 баллов

**RESULT:
hive> select artist_lastfm, scrobbles_lastfm from artists order by scrobbles_lastfm desc limit 0,1;
**RESULT:
screenshot max_artist.png

b) Самый популярный тэг на ластфм - 10 баллов

**RESULT:
hive> select tag, count(*) as tag_count from artists lateral view explode(split(tags_lastfm, ';')) t as tag where tag != '' group by tag order by tag_count desc limit 1;
**RESULT:
hive_2_b.png

c) Самые популярные исполнители 10 самых популярных тегов ластфм - 10
баллов

**RESULT:
with t as (select  tagname, artist_lastfm,  listeners_lastfm  from artists  lateral view explode(split(tags_lastfm, ';')) tags as tagname where tagname != ''),
     top_tags as (select tagname, count(*) cnt from t group by tagname order by cnt desc limit 10) 
select * 
     from 
     (select t.tagname, t.artist_lastfm, row_number() over (partition by t.tagname order by listeners_lastfm desc) rn, listeners_lastfm from t
         join top_tags
         on t.tagname = top_tags.tagname 
     ) t0
     where rn = 1;

**RESULT:
hive_2_c.png

d) Любой другой инсайт на ваше усмотрение - 10 баллов

**RESULT:
most nonstable artist
select artist_lastfm,  max(scrobbles_lastfm) - min(scrobbles_lastfm)  as spread  from artists group by artist_lastfm order by spread desc limit 0,5;

**RESULT:
hive_2_d.png
