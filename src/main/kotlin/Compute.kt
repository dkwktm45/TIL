import java.lang.IllegalArgumentException

fun main() {
    // 람다식
    compute(1, 1, { a, b -> a + b })
    compute(1, 1) { a, b -> a + b }

    // 익명함수
    compute(4, 3, fun(a: Int, b: Int): Int {
        return a + b
    })
    compute(4, 3, fun(a: Int, b: Int) = a + b)

    // 위 두 가지 방식은 함숫값 또는 함수 리터럴이라고 불린다.
    // 함숫값/함수 리터럴 : 일반 함수와 달리 변수로 간주하거나 파라미터에 넣을 수 있는 함수
    // 람다 : 이름이 없는 함수

    // 람다식과 익명 함수의 차이 -> 반환 타입을 적을 수 있고 없고의 차이 및
    // 람다식 안에는 return을 쓸 수 없고 익명 함수 안에는 return을 쓸 수 없다.
    // 익명일 경우
    iterate(listOf(1, 2, 3, 4), fun(num) {
        if (num == 3) {
            return
        }
        println(num)
    })
    // 람다인 경우 -> 못하는 이유는 return이라는 키워드는 fun을 탈출하는 의미이기 때문이다.
    // iterate(listOf(1,2,3,4), {num -> return num})
}

fun compute(num1: Int, num2: Int, op: (Int, Int) -> Int): Int {
    return op(num1, num2)
}

fun iterate(numbers: List<Int>, exec: (Int) -> Unit) {
    for (number in numbers) {
        exec(number)
    }
}

fun calculate(num1: Int, num2: Int, oper: Operator) = oper.calFunc(num1, num2)
enum class Operator(
    private val oper: Char,
    val calFunc: (Int, Int) -> Int
) {
    PLUS('+', { a, b -> a + b }),
    MINUS('+', { a, b -> a - b }),
    MULTIPLY('+', { a, b -> a * b }),
    DIVIDE('+', { a, b -> a + b }),
}