package me.hyuni

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MockController {
    @RequestMapping("/delay")
    fun delay(): String {
        val sleepTime = 30L
        Thread.sleep(sleepTime)
        return "took $sleepTime ms"
    }
}
