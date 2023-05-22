package meundi.graduationproject.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
// 로그인 정보가 없으면 접근 거부
@Slf4j
public class ReviewLoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        log.info("사용자 인증 필터 실행{}", requestURI);
        HttpSession session= request.getSession(false);
        if(session==null || session.getAttribute("userId") == null){
            response.sendRedirect("/review");
            log.info("접근거부");
            return false;
        }
        log.info("접근 가능");
        return true;
    }
}
