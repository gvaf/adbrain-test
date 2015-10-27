import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import com.datastax.spark.connector._
import com.datastax.spark.connector.rdd._
import com.datastax.spark.connector.cql.CassandraConnector
import com.datastax.driver.core._
import com.datastax.spark.connector.rdd.reader.RowReaderFactory
import scala.collection.mutable.ListBuffer

object AdWeather
{
	case class WeatherEvent (
		stationID:String, 
		stationName:String, 
		year:Int,
		month:Int,
		day:Int,
		latitude:Double,
		longitude:Double,
		maxTemp:Double,
		minTemp: Double)

	def main(args: Array[String]) 
    	{
		val conf = new SparkConf(true)
					.setAppName("AdWeather")
					.set("spark.cassandra.connection.host", "127.0.0.1")

        	val sc = new SparkContext(conf)

		val rdd = sc.cassandraTable[WeatherEvent]("adbrain_weather_data", "weather_data")

		// Q1: Calculate the maximum and minimum temperature per year.
		val maxTemperaturePerYear = rdd.map(x => (x.year, x.maxTemp))
					 	.reduceByKey((x,y) => math.max(x,y))
						.sortByKey()
						.collect()
	
		println("\nThe maximum temperature per year:")
		maxTemperaturePerYear.foreach(println)

		val invalidValue: Double = -9999.0

		// We filter out the invalid values and we calculate the minimum temperature per year.
		val minTemperaturePerYear = rdd.filter(_.minTemp != invalidValue)
						.map(x => (x.year, x.minTemp))
					 	.reduceByKey((x,y) => math.min(x,y))
						.sortByKey()
						.collect()
	
		println("\nThe minimum temperature per year:")
		minTemperaturePerYear.foreach(println)

		// Q2. Find the number of stations
		val numberStations = rdd.map(x => x.stationID).distinct().count()
		println("\nNumber of stations = " + numberStations)
				

		// Q3 Find the average maximum temperature per month
		val avgMaxTempPerMonth = rdd.filter(_.maxTemp != invalidValue)
						.map(x => (x.month, x.maxTemp))
						.mapValues((_, 1))
						.reduceByKey((x, y) => (x._1 + y._1, x._2 + y._2))
						.mapValues{ case (sum, count) => (sum.toDouble) / count }
						.sortByKey()
						.collect()

		println("\nAvg maximum temperature per month:")

		val months: Seq[String] = Seq("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

		avgMaxTempPerMonth.map(x => (months(x._1 - 1), x._2)).foreach(println)		


		// Q4: Find the station and the day with the minimum temperature
		val stationWithMinTemp = rdd.filter(_.minTemp != invalidValue)
						.map(x => ((x.stationName, x.year, x.month, x.day), x.minTemp))
						.reduceByKey( (x,y) => math.min(x,y) )
						.min()(Ordering[Double].on(_._2))

		println("\nStation with the minimum temperature: " + stationWithMinTemp)						
		
		// Q5: Find the top 10 highest temperatures on January
		val topTenHighTemp = rdd.filter(_.maxTemp != invalidValue)
					.filter(_.month == 1)
					.map( x => ((x.year, x.month, x.day, x.stationName), x.maxTemp) )
					.takeOrdered(10)(Ordering[Double].reverse.on(x => x._2))

		println("\nTop 10 highest temperatures on January: ")
		topTenHighTemp.foreach(println)
	}
}
