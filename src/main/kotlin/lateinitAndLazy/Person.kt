package lateinitAndLazy

import kotlin.properties.Delegates

// 1
class Person(
    private var name: String = "홍길동",
) {
    val isKim: Boolean
        get() = this.name.startsWith("김")
    val maskingName: String
        get() = name[0] + (1 until name.length).joinToString("") { "*" }
}

// 2
class Person2 {
    var name: String? = null
    val isKim: Boolean
        get() = this.name!!.startsWith("김")
    val maskingName: String
        get() = name!![0] + (1 until name!!.length).joinToString("") { "*" }
}

// 3
class Person3 {
    lateinit var name: String
    val isKim: Boolean
        get() = this.name.startsWith("김")
    val maskingName: String
        get() = name[0] + (1 until name.length).joinToString("") { "*" }
}


// 4
class Person4 {
    private var _name: String? = null
    val name: String
        get() {
            if(_name == null) {
                Thread.sleep(2000)
                this._name = "test"
            }
            return this._name!!
        }
}

// 4
class Person5 {
    val name: String by lazy {
        Thread.sleep(2000L)
        "test"
    }
}

// 4
class Person8 {
    // observable : 변수에 초기 값을 지정하고 값이 변경될 때마다 함수를 호출할 수 있습니다.
    var name: Int by Delegates.observable(20 ){
        property, oldValue, newValue ->
        println("이전값 : ${oldValue} 새로운 값 ${newValue}")
    }
}

class Person9 {
    // vetoable : obervable와 유사하지만 boolean을 반환한다.
    var name: Int by Delegates.vetoable(20 ){
            property, oldValue, newValue ->
        newValue >= oldValue
    }

    // 또 다른 프로퍼티로 위임하기
    @Deprecated("age를 사용하세요!", ReplaceWith("age"))
    var num: Int = 10
    // :: 두개를 사용하면 위임 객체가 된다.
    var age: Int by this::num
}

class Person10(map: Map<String,Any>) {
    val name: String by map
    val age: Int by map
}
fun main() {
    // 이때 클래스 인스턴스화가 이루어지며, name에 "최대현"이 들어간다.
    val person = Person("이진영")
    // 만약 인스턴스화 시점을 바꾸고 싶다면? 또한 초기값을 넣고 싶지 않을 때?
    // 1. 생성자 기본값을 지정한다 val이 아닌 var로!! -> Person1
    // 2. name을 nullalbe로 하자! -> Person2
    // 3. latenint을 사용한다. -> 다만 primitive type은 불가능하다. (nullable하지 않기 때문!)


    // 변수를 초기화 할 때 지정된 로직을 1회만 실행 시키고 싶다!
    // 1. backing property 사용 / ex) _?? -> 4
    // 2. by lazy 사용 -> 5

    // 둘의 차이 : latenint은 언제든지 초기화를 매번 사용할 수 있지만 lazy의 초기화 로직은 변수 선언과 동시에 한 곳에만 위치할 수 있다.

    // 기타 lainit
    val test = Person8();
    test.name = 1
    test.name = 30

    val mapTest = Person10(mapOf("test" to "1"))
    println(mapTest.name)
    println(mapTest.age)
}
