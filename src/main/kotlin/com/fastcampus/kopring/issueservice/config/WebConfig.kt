package com.fastcampus.kopring.issueservice.config

import com.fastcampus.kopring.issueservice.exception.UnauthorizedException
import com.fastcampus.kopring.issueservice.utils.JWTUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport

@Configuration
class WebConfig(
    private val authUserHandlerArgumentResolver: AuthUserHandlerArgumentResolver,
) : WebMvcConfigurationSupport() {


    override fun addArgumentResolvers(argumentResolvers: MutableList<HandlerMethodArgumentResolver>) {
        argumentResolvers.apply {
            add(authUserHandlerArgumentResolver)
        }
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/**")
            .addResourceLocations(*CLASSPATH_RESOURCE_LOCATIONS)
    }

    companion object {
        private val CLASSPATH_RESOURCE_LOCATIONS = arrayOf(
            "classpath:/META-INF/resources/",
            "classpath:/resources/", "classpath:/static/", "classpath:/public/"
        )
    }

}

@Component
class AuthUserHandlerArgumentResolver(
    @Value("\${jwt.issuer}") private val issuer: String,
    @Value("\${jwt.secret}") private val secret: String,
) : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean =
        AuthUser::class.java.isAssignableFrom(parameter.parameterType)

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        val authHeader = webRequest.getHeader("Authorization") ?: throw UnauthorizedException()
        val token = authHeader.split(" ")[1]

        // 현재는 JWT 토큰의 issuer,secret만 일치하면 전적으로 토큰을 신뢰하지만
        // 이게 뚫려서 토큰을 변조하면 보안적으로 취약할 수 있으므로 서버 통신이나 레디스 캐시를 사용할 수 있다
        val decodedJWT = JWTUtils.decode(token, secret, issuer)

        return with(decodedJWT) {
            AuthUser(
                userId = claims["userId"]!!.asLong(),
                profileUrl = claims["profileUrl"]?.asString(),
                username = claims["username"]!!.asString(),
                email = claims["email"]!!.asString(),
            )
        }
    }
}

data class AuthUser(
    val userId: Long,
    val profileUrl: String?,
    val username: String,
    val email: String,
)

