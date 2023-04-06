package meundi.graduationproject.domain.login.converter;

import meundi.graduationproject.domain.login.SocialLoginType;
import org.springframework.core.convert.converter.Converter;

import java.lang.annotation.Annotation;

public class SocialLoginTypeConverter implements Converter<String, SocialLoginType> {

    @Override
    public SocialLoginType convert(String s){
        return SocialLoginType.valueOf(s.toUpperCase());
    }
}
