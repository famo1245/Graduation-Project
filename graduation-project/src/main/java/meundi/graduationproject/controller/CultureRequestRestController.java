package meundi.graduationproject.controller;


import java.net.BindException;
import java.util.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meundi.graduationproject.domain.*;
import meundi.graduationproject.domain.DTO.CultureRequestDTO;
import meundi.graduationproject.domain.DTO.ReviewDTO;
import meundi.graduationproject.domain.DTO.response.CultureRequestResponseDTO;
import meundi.graduationproject.service.CultureRequestService;
import meundi.graduationproject.service.CultureService;
import meundi.graduationproject.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/culture-request")
public class CultureRequestRestController {

    private final MemberService memberService;
    private final CultureRequestService cultureRequestService;


    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> requestList() {
        Map<String, Object> message = new HashMap<>();
        List<CultureRequest> list = cultureRequestService.findRequestAll();
        List<CultureRequestDTO> result = new ArrayList<>();
        for (CultureRequest c : list) {
            CultureRequestDTO dto = new CultureRequestDTO();
            dto.setDTO(c);
            result.add(dto);
        }

        Collections.reverse(result);

        message.put("requests", result);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @GetMapping("/requestDetail/{request_id}") /*리뷰 하나를 클릭 시, 리뷰 자세히 보기*/
    public ResponseEntity<Map<String, Object>> requestDetail(@PathVariable Long request_id) {

        Member member;
        CultureRequestResponseDTO responseDTO = new CultureRequestResponseDTO();
        responseDTO.setRequestDetail(cultureRequestService.findOne(request_id));
        Map<String, Object> message = new HashMap<>();
        message.put("requestDetail", responseDTO);

        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @GetMapping("/requestDetail/{request_id}/delete")
    public ResponseEntity<Map<String, Object>> requestDelete(@PathVariable Long request_id,
        HttpSession session) {
        CultureRequest request = cultureRequestService.findOne(request_id);

        if (Objects.equals(request.getMember().getId(), (Long) session.getAttribute("userId"))) {
            cultureRequestService.deleteCultureRequest(cultureRequestService.findOne(request_id));
        }
        Map<String, Object> message = new HashMap<>();
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @GetMapping("/write")
    public ResponseEntity<Map<String, Object>> requestAddForm() {
        Map<String, Object> message = new HashMap<>();
        message.put("data", new CultureRequest());
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @PostMapping("/write")
    public ResponseEntity<Map<String, Object>> addRequest(@RequestBody CultureRequest cultureRequest,
                                                          @RequestParam Long userId) {

        Map<String, Object> message = new HashMap<>();
        cultureRequest.setRequestDateTime(LocalDateTime.now());
        Member member = memberService.findById(userId);
        cultureRequest.setMember(member);
        cultureRequestService.insertCultureRequest(cultureRequest);

        message.put("result", "ok");
        return ResponseEntity.status(HttpStatus.OK).body(message);

    }

    @GetMapping("requestDetail/{request_id}/edit")
    public ResponseEntity<Map<String, Object>> editRequestForm(
        @PathVariable("request_id") Long requestId,HttpSession session) {
        // 세션이 없거나 세션 사용자와 리뷰 작성자가 다르면 거부 (URL 로 직접 접근시, 거부)
        if (session.getAttribute("userId") != null) {
            Member member = memberService.findById((Long) session.getAttribute("userId"));
            if (!Objects.equals(member.getId(),
                cultureRequestService.findOne(requestId).getMember().getId())) {
            }
        }
        Map<String, Object> message = new HashMap<>();
        CultureRequest request = cultureRequestService.findOne(requestId);

        message.put("data", request);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @PostMapping("requestDetail/{request_id}/edit")
    public ResponseEntity<Map<String, Object>> editRequest(
        @Validated @RequestBody CultureRequestDTO request, BindingResult bindingResult,
        @PathVariable("request_id") Long requestId) throws BindException {
        // 검증 로직
        if (!StringUtils.hasText(request.getTitle())) {
            bindingResult.rejectValue("title", "required");
        }
        if (!StringUtils.hasText(request.getContents())) {
            bindingResult.rejectValue("requestContents", "required");
        }

        /* 검증에 문제 발생 시, 다시 add */
        if (bindingResult.hasErrors()) {
            throw new BindException();
        }
        cultureRequestService.updateCultureRequest(requestId, request.getTitle(),
            request.getContents());

        Map<String, Object> message = new HashMap<>();
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
}
