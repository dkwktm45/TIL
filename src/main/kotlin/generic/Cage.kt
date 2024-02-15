package generic

fun main() {
  val cage = Cage()
  // 형 변환을 어떻게 해야 하는지 알아보자.
  cage.put(Carp("잉어"))
  // 문제의 코드
  // val carp: GoldFish = cage.getFirst()

  // 1. 인위적인 변경 -> 하지만 런타임시 에러가 발생
  val carp: GoldFish = cage.getFirst() as GoldFish

  // 2. 제넥릭을 사용하자! -> 제네릭을 사용해서 타입을 고정시킨다. 그럼 미리 사전에 알 수 있다.
  val goldFishCage = Cage2<GoldFish>()
  goldFishCage.put(GoldFish("금붕어"))

}

class Cage {
  private val animals: MutableList<Animal> = mutableListOf()

  fun getFirst(): Animal {
    return animals.first()
  }

  fun put(animal: Animal) {
    this.animals.add(animal)
  }

  fun moveFrom(cage: Cage) {
    this.animals.addAll(cage.animals)
  }
}
