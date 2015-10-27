
USE adbrain_weather_data;

COPY weather_data(station_id, station_name, latitude, longitude, year, month, day, max_temp, min_temp) FROM 'csv/sample_out.csv';
