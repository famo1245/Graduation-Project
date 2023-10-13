package meundi.graduationproject.config;

import meundi.graduationproject.interceptor.ReviewLoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String path = new File("").getAbsoluteFile() + "\\";
        registry.addResourceHandler("/**").addResourceLocations("file:///" + path);
    }
}
