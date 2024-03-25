package dsl

import java.time.LocalDate

class OperatorOverloading {
}

data class Point(
  val x: Int,
  val y: Int,
) {
  // operator를 사용해야 연선자 오버로딩이 가능하다.
  operator fun unaryMinus(): Point {
    return Point(-x, -y)
  }

  operator fun unaryPlus(): Int {
    return x
  }

  operator fun inc(): Point {
    return Point(x + 1, y + 1)
  }
}

fun main() {
  var point = Point(20, -10)
  /*
  * - / ++ 를 사용하면 각각 unaryMinus / inc 가 실행된다.
  * 이러한 이유는 Kotlin 에서 내부적으로 설정을 해줬다.
  * */
  println(-point)
  println(++point)

  // operator 활용 사례
  var list = listOf("A", "B", "C") // 반면 val변수라면 에러가 뜬다.
  list += "D" // 이경우는 plus되면서 새로운 list가 덮어씌워진다.
  val list2 = mutableListOf("A", "B", "C")
  list2 += "B" // 기본적으로 +는 add함수가 적용됨

  // 응용
  // 2023-01-04
  LocalDate.of(2023, 1, 1).plusDays(3)

  // 2023-01-04
  // 2023-12-29 <- 조금 문제가 있다~~
  LocalDate.of(2023, 1, 1) + Days(3)

  LocalDate.of(2023, 1, 1) + 3.d
}

data class Days(val day: Long)

val Int.d: Days
  get() = Days(this.toLong())

operator fun LocalDate.plus(days: Days): LocalDate {
  return this.plusDays(days.day)
}



