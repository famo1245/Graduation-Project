package meundi.graduationproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meundi.graduationproject.domain.CultureRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/culturerequest")
public class CultureRequestController {

    @GetMapping("/list")
    public String requestList(Model model) {
        List<CultureRequest> requestList = new ArrayList<>();
        model.addAttribute("requestList", requestList);
        return "cultureRequest/requestList";
    }

    /*
    @GetMapping("/home")
    public String SearchRequest(@ModelAttribute("requestSearch") CultureRequest requestSearch, Model model) throws MalformedURLException{
        List<CultureRequest> requestsAll = requestSearch.SearchRequest(requestSearch);
        model.addAttribute("requestAll", requestsAll);
        return "culture-request/requestHome";
    }
    @GetMapping("/addRequest")
    public String addRequest(Model model){
        model.addAttribute("culture-request", new CultureRequest());
        return ""
    }

     */
}
