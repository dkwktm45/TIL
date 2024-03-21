package lateinitAndLazy

import kotlin.reflect.KProperty

class ByLazy {
    // by lazy를 구현 하면 아래와 같이 가능하다.
    // 이러한 패턴을 위임패턴이라고 한다.
    // 결국 backing property를 사용한 꼴이다.
    // 그럼 어떻게 각각의 변수 초기화를 어떻게 초기화를 시키는가?
    // - 내부에는 getValue / setValue가 있다.
    /*
    * getValue()
    * @kotlin.internal.InlineOnly
    * public inline operator fun <T> Lazy<T>.getValue(thisRef: Any?, property: KProperty<*>): T = value
    * 'thisRef:R' : 위임 프로퍼티를 갖고 있는 클래스의 인스턴스
    * 'property: KProperty<*>' : 위임 시킨 프로퍼티 정보
    * 아래의 Person6을 비교 한다면 thisRef에는 Person6가 오고 property에는 name이 온다.
    * */
}

class Person6 {
    // 또한 내부에서는 이렇게 불린다.
    // "private val delegateProperty" : 위임 프로퍼티
    // "LazyInitProperty" -> "by lazy()" 위임 객체
    private val delegateProperty = LazyInitProperty {
        Thread.sleep(2_000L)
        "김수한무"
    }
    val name: String
        get() = delegateProperty.value
}
class Person7 {
    private val name: String by lazy {
        Thread.sleep(2_000L)
        "김수한무"
    }

}
class LazyInitProperty<T>(val init: () -> T) {
    private var _value: T? = null
    val value: T
        get() {
            if (_value == null) {
                this._value = init()
            }
            return _value!!
        }

    // getValue 직접 구현
    operator fun getValue(thisRef: Any, property: KProperty<*>): T{
        return value
    }
}