package generic

fun main() {

  // 사용 지점 변성
  val fishCage = Cage3<Fish>()
  val animalCage: Cage3<out Animal> = fishCage

}

// 선언 지점 변성
class Cage3<out T>(
  private val animals: MutableList<T> = mutableListOf()
) {

  fun getFirst(): T {
    return this.animals.first()
  }

  fun getAll(): List<T> {
    return this.animals
  }
}
