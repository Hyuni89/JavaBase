package me.hyuni

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MockController {
    @RequestMapping("/delay")
    fun delay() = "test"
}
