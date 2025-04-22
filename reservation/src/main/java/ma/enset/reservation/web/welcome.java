package ma.enset.reservation.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class welcome {
    @GetMapping
    public String welcome() {
        return "welcome";
    }

}
