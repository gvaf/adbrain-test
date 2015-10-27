#!/usr/bin/env python
# Load dataset into cassandra
#
# Dependencies:
# apt-get install build-essential
# apt-get install python-dev
# sudo apt-get install libev4 libev-dev
# pip install cassandra-driver
#

import csv
import sys
from cassandra.cluster import Cluster

input = 'csv/625167.csv'

# Extract fields from compact date YYYYmmdd 20000101 -> (2000, 01, 01)
def convertDate(date):
	return	(int(date[0:4]), int(date[4:6]), int(date[6:8]))

cluster = Cluster()
session = cluster.connect('adbrain_weather_data')

query = "INSERT INTO weather_data (station_id, station_name, latitude, longitude, year, month, day, max_temp, min_temp) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
prepared = session.prepare(query)

# STATION,STATION_NAME,ELEVATION,LATITUDE,LONGITUDE,DATE,TMAX,TMIN
with open(input, 'rb') as csvfile:

	weather_reader = csv.reader(csvfile, delimiter=',')
	next(weather_reader, None)	# ignore header
	records = 0

	for row in weather_reader:
		if row:
			station_id = row[0]			
                        station_name = row[1]
			# ignore elevation
                        latitude = float(row[3])
                        longitude = float(row[4])
                        (year, month, day) = convertDate(row[5])
                        max_temp = float(row[6])
                        min_temp = float(row[7])

			bound = prepared.bind((station_id, station_name, latitude, longitude, year, month, day, max_temp, min_temp))
			session.execute(bound)
			records = records + 1
			print("Inserting record #%d: %s %s" % (records, station_id, station_name))

#result = session.execute("select * from weather_data")
#for x in result:
#	print(x)
