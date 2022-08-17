## Cancellation and timeouts

### Cancellation

coroutine에서 취소(cancellation)는 협력적이다. 이게 무슨 말인고 하면, 취소 가능한 coroutine을 취소하게 되면 작업중인 coroutine은 취소 이벤트를 확인하고 `CancellationException` 예외를 던지고 coroutine을 취소한다. 그런데 coroutine이 계속해서 동작하는 상황에선 취소 이벤트를 확인 할 수 없고, 따라서 해당 coroutine이 모두 동작한 후에야 취소될 수 있다. 이러한 상황(내가 원하는 때에 coroutine을 취소하지 못하는 상황)을 해결하기 위해 두가지 방법이 있는데, 첫번째로 주기적으로 suspending function (ex. `yield`)을 호출하며 취소 이벤트를 확인하는 것이다. 나머지 방법으로는 명시적으로 coroutine이 취소되었는지 확인을 하는 방법이 있다. `coroutineScope`내의 `isActive`가 적절한 예라고 볼 수 있다.

`kotlinx.coroutines`에 포함된 모든 suspending function은 취소 가능한 함수들이다. 이러한 취소 가능한 함수들은 취소될 때 `CancellationException` 예외를 던지고 취소된다. 이렇게 던져진 예외를 가지고 예외 처리 구문 역시 태울 수 있다.  
예외를 핸들링 할 때 `finally` 구문은 반드시 실행되며 다양한 작업을 할 수 있다. 그런데 coroutine에서 `CancellationException` 예외로 `finally` 구문에 들어갔을 때 해당 블록에서 suspending function은 실행 되지 않는다. 왜냐하면 이미 coroutine이 종료 중이기 때문이다. 일반적인 경우에 이런 케이스는 문제될 것이 없다. 그러나 아주 특별한 케이스 (~나는 잘 생각 안나는데~)에서 suspending function을 호출해야 할 필요가 있을 수 있다. 이 땐 Non-Cancellable context를 사용해서 suspending function을 실행 할 수 있다. `withContext(NonCancellable) { ... }`

### Timeouts

coroutine에서 timeout이 필요할 수 있다. 이때 각 coroutine을 트래킹 하며 timeout을 계산 할 수도 있겠지만 너무 번거롭고 어려울 것이다. 이때 `withTimeout`을 사용하면 간단하게 timeout을 처리 할 수 있다. timeout이 발생되면 `TimeoutCancellationException` 예외가 발생한다. 이것은 이전에 보았던 `CancellationException`의 하위 클래스이다.
#### ex

```kotlin
var acquired = 0

class Resource {
    init { acquired++ }
    fun close() { acquired-- }
}

fun main() {
    runBlocking {
        repeat(100_000) {
            launch { 
                val resource = withTimeout(100) {
                    delay(99)
                    Resource()
                }
                resource.close()
            }
        }
    }
    println(acquired)
}
```

공용 리소스를 참조하는 간단한 예제이다. `Resource` 객체가 생성될 때 공용 리소스인 `acquired`가 증가하고, `close`를 호출함으로 `acquired`를 감소시킨다. timeout이 존재하긴 하지만 정상적인 경우를 생각하면 맨 마지막의 `acquired`의 값은 0으로 출력되어야 할 것이다. 그러나 실제로 출력되는 값은 0이 아닌 값이다. 이는 timeout으로 인해 `TimeoutCancellationException` 예외가 발생하고 coroutine이 종료되면서 `close`를 통한 리소스 반납이 제대로 이루어지지 않았기 때문이다. 코드상으론 timeout도 발생되지 않아야 하지만 100K의 coroutine이 실행되며 1ms의 오차가 발생해서 timeout이 발생하는 coroutine에 의해 이러한 현상이 나타난다.

이를 해결하기 위해선 리소스 반납 `close` 부분이 반드시 실행되어야 한다.
```kotlin
repeat(100_000) {
	launch {
    	val resource: Resource? = null
        try {
        	withTimeout(100) {
            	delay(99)
                resource = Resource()
            }
        } finally {
        	resource?.close()
        }
    }
}
```
위와 같이 수정되면 깔끔이 해결되는 것을 확인 할 수 있다.

> 위 예제에서 정상적으로 동작했을 때 `acquired` 값이 과연 0이 맞을까 의문이 들 수 있다. 우리가 많이 사용하는 thread를 통해 같은 예제를 만들었으면 `acquired`에 접근할 때 lock을 잡지 않으면 반드시 값이 틀어지기 때문에 0을 보장할 수 없다. 그러나 coroutine에서 위의 launch로 실행되는 100K의 coroutine job이 모두 동일한 Main thread에서 실행되기 때문에 동시에 `acquired`에 접근하는 일이 없기 때문에 0의 값을 보장할 수 있다.  
