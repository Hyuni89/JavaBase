# Coroutine?
co + routine의 합성어로 어떤 routine을 co(함께) 해결해 나간다는 의미이다. coroutine은 light-weight(경량화) thread라고 생각 할 수 있는데 동시성을 가지고 코드를 동작한다는 개념적으로 thread와 유사한 면을 가지고 있다. 그러나 coroutine은 어떤 thread에 매여 동작하는 것이 아니라 여러 thread에 걸쳐서 수행 될 수도 있다.

## Suspending function
coroutine에서 많이 보이는 suspending function은 thread를 block하지 않는다. 대신 다른 coroutine이 해당 thread에서 동작할 수 있게 한다.

## Structured concurrency
새로운 coroutine은 제한된 특정 coroutine scope 내에서만 실행될 수 있다. 다시말해, children coroutine이 실행되고 있는 경우엔 parent coroutine은 완료되지 않는다는 말이다.
