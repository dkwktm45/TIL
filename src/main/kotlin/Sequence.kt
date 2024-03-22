class Sequence {
}

data class MyFruit(
    val name: String,
    val price: Long,
)
fun main() {
    val fruits = listOf(
        MyFruit("사과", 100),
        MyFruit("배", 400),
        MyFruit("망고", 1200),
    )

    // 이러한 iterable은 중간중간에 컬렉션을 만든다.
    val avg = fruits
        .filter { it.name == "사과" }
        .map { it.price }
        .take(10_000)
        .average()

    // asSequence를 사용한다면 중간중간 컬렉션을 만들지 않는다.
    // asSequence를 사용한다면 하나의 값마다 iterable을 거친다.
    val avg2 = fruits.asSequence()
        .filter { it.name == "사과" } // 중간 연산
        .map { it.price } // 중간 연산
        .take(10_000) // 중간 연산
        .average() // 최종 연산
    /*
    * 단 데이터 양이 적다면 iterable이 효율적일 수 있다.
    * 또한 어떤 연산을 사용할 지에 따라 시간이 달라질 수 있다.
    * */
}