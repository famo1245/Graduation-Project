package meundi.graduationproject.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// 로그인 정보가 없으면 접근 거부
@Slf4j
public class ReviewLoginCheckInterceptor implements HandlerInterceptor {
    final String REVIEW_URL = "/review";
    final String MEMBER_URL = "/members";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        log.info("Check user's authorization to={}", requestURI);

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            // 어느 url 에서 넘어왔는지 확인
            if (requestURI.contains(REVIEW_URL)) {
                response.sendRedirect(REVIEW_URL);
            } else if (requestURI.contains(MEMBER_URL)) {
                response.sendRedirect("/");
            }

            log.info("접근거부");
            return false;
        }

        log.info("접근 가능");
        return true;
    }
}
