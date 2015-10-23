package com.adbrain.TruncateNumber
{

import scala.collection.mutable.ListBuffer

object Truncate
{

/**
* @param num An integer and is assumed to be at least 0.
* @return A list of integers with the first entry being num, and the subsequent ones
* are gotten by ommiting the left hand most digit one by one.
* Eg num = 0 returns List(0).
* num = 1234 returns List((1234,234, 34, 4)
*
*/
def leftTruncate(num: Int) : List[Int] =
{
    if (num < 0)
    {
        throw new IllegalArgumentException("num cannot be negative");
    }

    var result: ListBuffer[Int] = new ListBuffer[Int]
    var power10: Int = 1
    var n: Int = num
    var prev: Int = 0

    do
    {
      val digit: Int = n % 10

      n = n / 10

      prev = digit * power10 + prev

      result.prepend(prev)

      power10 = power10 * 10
    } while (n != 0)

    return result.toList
}

}
 
} // end of package
