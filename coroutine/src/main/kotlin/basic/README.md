## Coroutines Basics

### Suspending function

coroutine에서 많이 보이는 suspending function은 thread를 block하지 않는다. 대신 다른 coroutine이 해당 thread에서 동작할 수 있게 한다.

### Structured concurrency

새로운 coroutine은 제한된 특정 coroutine scope 내에서만 실행될 수 있다. 다시말해, children coroutine이 실행되고 있는 경우엔 parent coroutine은 완료되지 않는다는 말이다.

#### ex
```kotlin
suspend fun doWorld() = coroutineScope {
    launch {
        delay(2000L)
        println("World 2")
    }
    launch {
        delay(1000L)
        println("World 1")
    }
    println("Hello")
}

fun main() = runBlocking {
    doWorld()
    println("Done")
}
```

`runBlocking`은 coroutine builder로 coroutine 영역과 coroutine이 아닌 영역의 다리 역할을 한다.  
`launch`는 coroutine builder로 새로운 coroutine이 동시적으로 동작하게 하는 역할을 한다.  
`doWorld`는 `suspend` 키워드 덕분에 suspending function이 되었다. 이는 thread를 block하지 않고 다른 coroutine이 해당 thread를 사용할 수 있겠금 suspend 한다는 것을 의미한다.  
`coroutineScope`는 coroutine builder로 새로운 coroutine 영역을 지정할 수 있다. Structured concurrency principle이 적용되어 scope 내의 children coroutine이 완료되기 전에 parent coroutine은 종료될 수 없다. 이는 `runBlocking`과 비슷해 보일 수 있는데, 가장 큰 차이로 `runBlocking`은 thread를 block하는 일반 보통의 함수지만 `coroutineScope`는 thread를 block하지 않고 다른 일을 할 수 있도록 하는 suspending function이다.
  
`runBlocking`에서 suspend 함수인 `doWorld`를 호출한다. 하지만 `launch`가 붙지 않았기 때문에 순차적인 호출이다. `doWorld`에서 첫번째 `launch`로 2초간 대기 후 `World 2`를 출력하는 coroutine을 동작시킨다. 그리고 다음 `launch`에서 1초간 대기 후 `World 1`을 출력하는 coroutine을 동작시킨다. 그리고 `Hello`를 출력한다. `doWorld` 함수 내부의 코드가 다 동작되었지만 해당 scope 내의 두 번의 `launch`로 실행중인 children coroutine이 종료되지 않았기 때문에 `doWorld`를 실행하는 `coroutineScope`는 children coroutine이 종료될 때까지 대기한다. 1초 후에 `World 1`이 출력되고 나머지 1초 후에 `World 2`가 출력 된 이후 모든 children coroutine이 종료되고 `coroutineScope`는 종료될 수 있다. 그 후에 `main` 블럭으로 나와 마지막 `Done`을 출력하고 프로그램은 종료하게 된다.
  
출력결과
```
Hello
World 1
World 2
Done
```
