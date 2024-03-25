
/*
* SAM : single abstract interface
* Java에서는 SAM interface를 람다로 인스턴스화 할 수 있다.
* 반면 Kotlin에서는 람다식으로 인스턴스화를 할 수 없다.
* */

fun add(a: Int, b: Int) = a + b

fun main() {

  val add1 = { a: Int, b: Int -> a + b }

  val add2 = fun (a: Int, b: Int) = a + b

  // 호출 가능 참조!
  ::add
  // 호출 가능 참조는 아래와 같이 응용도 가능하다.
  /*
  * Person::name.getter
  * "변수 이름"::a.getter
  *
  * 차이점 Java에서 호출 가능 참조 결과값이
  * Consumer/ Supplier같은 함수형 인터페이스이지만,
  * Kotline에서는 리플렉션 객체이다.
  *
  * */
  // 익명 클래스 가능
  val filter: StringFilter = object : StringFilter{
    override fun predicate(str: String?): Boolean {
      TODO("Not yet implemented")
    }
  }
  // 람다식 불가능.. 컴파일 에러가 뜬다.
  // val filterError: StringFilter = {s -> s.startsWidth("A")};
  // 어떤 인터페이스인지를 명시를 해야 가능 이러한 형태를 SAM 생성자라고 불린다.
  val filterError: StringFilter = StringFilter {s -> s.startsWith("A")};

  // 만약 파라미터로 쓴다면 람다식이 가능하다. 아래와 같이
  // 하지만 이러한 구체화된 경우와 암시적인 T를 제네릭을 사용하는 경우는 구체화된 interface를 사용하자!
  KStringFilter { it.startsWith("A") }
}

fun consumeFilter(filter: StringFilter) { }

fun <T> consumeFilter(filter: Filter<T>) {}

// Kotlin으로 하는 SAM Interface!
fun interface KStringFilter {
  fun predicate(str: String): Boolean
}
