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
	def main(args: Array[String]) 
    	{
		val conf = new SparkConf(true).setAppName("AdWeather").set("spark.cassandra.connection.host", "127.0.0.1")
        	val sc = new SparkContext(conf)

		val rdd = sc.cassandraTable("adbrain_weather_data", "weather_data")
		println(rdd.count)
		println(rdd.first)
	}
}
