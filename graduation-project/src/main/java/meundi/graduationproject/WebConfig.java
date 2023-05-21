package meundi.graduationproject;

import meundi.graduationproject.interceptor.ReviewLoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//    인터셉터 추가를 위한 Config
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ReviewLoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/review/reviewWrite");
    }
}
