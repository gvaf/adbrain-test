import org.scalatest._
import com.adbrain.TruncateNumber.Truncate._

class TruncateSpec extends FlatSpec with Matchers
{ 
    it should "throw IllegalArgumentException if the function is called with a negative number" in {
        intercept[IllegalArgumentException] {
            leftTruncate(-100);
        }
    }  

    it should "return the same number if the function is called with a single digit" in {
        for(i <- 0 to 9)
        {
            assert(leftTruncate(i) == List(i))
        }
    }

    it should "work for the maximum integer value" in {
        leftTruncate(Int.MaxValue) shouldEqual List(2147483647, 147483647, 47483647, 7483647, 483647, 83647, 3647, 647, 47, 7)
    }

    it should "work for numbers with multiple zeroes" in {
        leftTruncate(10000) shouldEqual List(10000, 0, 0, 0, 0)
    }

    it should "return the List(1234, 234, 34, 4) if the function is called with the value 1234" in {
        leftTruncate(1234) shouldEqual List(1234, 234, 34, 4)
    }

    it should "return the List(4321, 321, 21, 1) if the function is called with the value 4321" in {
        leftTruncate(4321) shouldEqual List(4321, 321, 21, 1)
    }

    it should "return the List(2015, 15, 15, 5) if the function is called with the value 2015" in {
        leftTruncate(2015) shouldEqual List(2015, 15, 15, 5)
    }

    it should "return the List(13, 3) if the function is called with the value 13" in {
        leftTruncate(13) shouldEqual List(13, 3)
    }    
}
