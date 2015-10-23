import com.adbrain.TruncateNumber.Truncate._
import scala.util.Try

object Truncate
{
    def main(args: Array[String]) 
    {
        val numbers:List[Int] = List(0, 2, 8, 9, 10, 13, 1000, 1234, 4321, 2015, 123456789, Int.MaxValue, -1, -100)
   
        numbers.map(x => (x, Try(leftTruncate(x)).toOption) )
               .collect { case (x, Some(value)) => (x, value) } 
               .foreach( x => println("Truncate of %d is %s.".format(x._1, x._2)))
    }

}
