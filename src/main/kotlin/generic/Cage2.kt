package generic

fun main() {
  /*
  * out : 생산자, 공변
  * in : 소비자, 반공면
  * */

  // 변수에 공변과 반공변을 넣는 방법
  val cage: Cage2<out Fish> = Cage2<GoldFish>()
  val cage2: Cage2<in GoldFish> = Cage2<Fish>()
}

class Cage2<T : Any> {
  private val animals: MutableList<T> = mutableListOf()

  fun getFirst(): T {
    return animals.first()
  }

  fun put(animal: T) {
    this.animals.add(animal)
  }

  // 공변
  fun moveFrom(otherCage: Cage2<out T>) {
    this.animals.addAll(otherCage.animals)
  }

  // 반공변
  fun moveTo(otherCage: Cage2<in T>) {
    otherCage.animals.addAll(this.animals)
  }
}
