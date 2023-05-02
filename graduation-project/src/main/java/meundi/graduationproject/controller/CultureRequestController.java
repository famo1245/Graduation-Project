package meundi.graduationproject.controller;

import lombok.extern.slf4j.Slf4j;
import meundi.graduationproject.domain.CultureRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/culture-request")
public class CultureRequestController {

    @GetMapping("/list")
    public String requestList(Model model) {
        List<CultureRequest> requestList = new ArrayList<>();
        model.addAttribute("requestList", requestList);
        return "cultureRequest/requestList";
    }
}
