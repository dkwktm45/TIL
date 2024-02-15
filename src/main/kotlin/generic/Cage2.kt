package generic

fun main() {
  val cage: Cage2<out Fish> = Cage2<GoldFish>()
}

class Cage2<T : Any> {
  // Todo MutableList은 무엇이지?
  private val animals: MutableList<T> = mutableListOf()

  fun getFirst(): T {
    return animals.first()
  }

  fun put(animal: T) {
    this.animals.add(animal)
  }
}
