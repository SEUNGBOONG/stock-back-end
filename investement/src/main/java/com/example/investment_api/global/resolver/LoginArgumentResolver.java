package com.example.investment_api.global.resolver;

import com.example.investment_api.global.annotation.Member;
import com.example.investment_api.global.exception.exceptions.jwt.NotFoundTokenException;
import com.example.investment_api.global.application.JwtTokenService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


@Component
@RequiredArgsConstructor
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String TOKEN_HEADER_NAME = "Authorization";

    private final JwtTokenService jwtTokenService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isLoginAnnotation = parameter.hasParameterAnnotation(Member.class);
        boolean isMemberIdType = Long.class.isAssignableFrom(parameter.getParameterType());
        return isLoginAnnotation && isMemberIdType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String tokenHeader = Optional.ofNullable(request.getHeader(TOKEN_HEADER_NAME))
                .orElseThrow(NotFoundTokenException::new);
        String token = tokenHeader.substring(7);
        return jwtTokenService.verifyAndExtractJwtToken(token);
    }
}
