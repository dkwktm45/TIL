package generic

class TypeErase {
}

fun checkList(data: Any) {
    // star projection : 해당 타입이 어떤 타입인지 모른다.
    if( data is List<*>) {
        // 어떤 타입인지 모르기 때문에 Any?로 표시한다.
        val element: Any? = data[0]
        // 어떤 타입인 모르기 때문에 데이터를 넣는 행위를 못 하게 한다.
        // data.add(3)
    }
}
fun <T> T.toSuperString(){
    // T::class는 클래스 정보를 가져오는 코드
    // 핵심은 T가 뭇엇인 런타임 때도 알 수 없기 때문에 오류가 난다.
    // println("${ T::class.java.name }:: $this")
}
fun main() {
    val num = 3
    num.toSuperString();
    // 하지만 우리는 T의 정보를 가져오고 싶을 때가 있다. - 1, 2
    // 1, 2번과 같이 할 수 있지만 inline refine으로 가능한다.
    val numbers = listOf(1,2,3f)
    println(numbers.hasAnyInstanceOfString<Int>())

    // 대표적인 T의 정보를 가져오는 Kotlin함수 또한 아래와 같다.
    println( numbers.filterIsInstance<Float>())
}
/*

// 1
fun List<*>.hasAnyInstanceOfString(): Boolean {
    return this.any { it is String }
}
// 2
fun List<*>.hasAnyInstanceOfInt(): Boolean {
    return this.any { it is Int }
}*/
//
inline fun <reified T> List<*>.hasAnyInstanceOfString(): Boolean {
    return this.any { it is T }
}