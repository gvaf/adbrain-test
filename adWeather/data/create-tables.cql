/*	 
    Schema for storing weather data from http://www.ncdc.noaa.gov/
*/

DROP KEYSPACE IF EXISTS adbrain_weather_data;

CREATE KEYSPACE adbrain_weather_data WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

use adbrain_weather_data;

CREATE TABLE weather_data (
	station_id text,
	station_name text,
	latitude double,
	longitude double,
	year int,
	month int, 
	day int,
	max_temp double,
	min_temp double,
	PRIMARY KEY ((station_id), year, month, day)
) WITH CLUSTERING ORDER BY (year DESC, month DESC, day DESC);


CREATE INDEX weather_index_year ON adbrain_weather_data.weather_data (year);

CREATE INDEX weather_index_month ON adbrain_weather_data.weather_data (month);

CREATE INDEX weather_index_day ON adbrain_weather_data.weather_data (day);
