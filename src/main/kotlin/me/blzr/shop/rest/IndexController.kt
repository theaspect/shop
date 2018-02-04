package me.blzr.shop.rest

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class HomeController {
    @RequestMapping("/catt")
    fun cat() = "/index.html"
}
