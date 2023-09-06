package com.streamhelper.microservices.game.draw.configuration

import com.streamhelper.microservices.game.draw.security.filter.DevelopFilterChain
import com.streamhelper.microservices.game.draw.security.filter.TokenFilterChain
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.web.cors.CorsUtils


@Configuration
@EnableWebSecurity
class WebSecurityConfig {

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { security ->
            security.ignoring().requestMatchers(AntPathRequestMatcher.antMatcher("/h2/**"))
            security.ignoring().requestMatchers(
//                AntPathRequestMatcher.antMatcher(HttpMethod.OPTIONS),
                AntPathRequestMatcher.antMatcher("/**"),
                AntPathRequestMatcher.antMatcher("/v2/api-docs/**"),
                AntPathRequestMatcher.antMatcher("/swagger.json"),
                AntPathRequestMatcher.antMatcher("/swagger-ui.html"),
                AntPathRequestMatcher.antMatcher("/swagger-ui/**"),
                AntPathRequestMatcher.antMatcher("/swagger-resources/**"),
                AntPathRequestMatcher.antMatcher("/webjars/**"),
                AntPathRequestMatcher.antMatcher("/api-docs/json"),
            )
        }
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()

    @Bean
    fun filterChain(
        http: HttpSecurity,
        apiKeyChain: TokenFilterChain,
        developFilterChain: DevelopFilterChain?
    ): SecurityFilterChain {
//        val configuration = CorsConfiguration()
//
//        configuration.addAllowedOrigin("*")
//        configuration.addAllowedHeader("*")
//        configuration.addAllowedMethod("*")
//        configuration.allowCredentials = true
//
//        val source = UrlBasedCorsConfigurationSource()
//        source.registerCorsConfiguration("/**", configuration)

        http.httpBasic().disable()
            .cors().disable() // .configurationSource(source).and()
            .csrf().disable()
//            .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
            .authorizeHttpRequests()
//            .requestMatchers(AntPathRequestMatcher.antMatcher("/api/**")).hasAnyRole("USER", "ADMIN")
//            .requestMatchers(
//                AntPathRequestMatcher.antMatcher("/api/**")
//            ).hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
            .requestMatchers(
                CorsUtils::isPreFlightRequest,
                AntPathRequestMatcher.antMatcher("/**/auth/**"),
                AntPathRequestMatcher.antMatcher("**"),
                AntPathRequestMatcher.antMatcher("/v2/api-docs/**"),
                AntPathRequestMatcher.antMatcher("/swagger.json"),
                AntPathRequestMatcher.antMatcher("/swagger-ui.html"),
                AntPathRequestMatcher.antMatcher("/swagger-ui/**"),
                AntPathRequestMatcher.antMatcher("/swagger-resources/**"),
                AntPathRequestMatcher.antMatcher("/webjars/**"),
                AntPathRequestMatcher.antMatcher("/api-docs/json"),
            ).permitAll()
            .requestMatchers(
                AntPathRequestMatcher.antMatcher("/api/v1/auth/**")
            ).permitAll()
//            .and().headers().addHeaderWriter(XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
//            .and().csrf().ignoringRequestMatchers("/h2/**")
            .anyRequest().permitAll()//authenticated()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .csrf().ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/api/**"))

        // 개발용 인증 체인이 존재하면 설정한다.
        // 개발용 인증 체인이 먼저 호출되도록 앞쪽으로 위치시킨다.
        if (developFilterChain != null) {
            http.addFilterBefore(developFilterChain, UsernamePasswordAuthenticationFilter::class.java)
        }
        // 실제 토큰 인증 체인을 개발용 인증 체인의 뒤에 위치시킨다.
        // 개발용 인증체인에서 값이 설정되더라고, 토큰이 존재하면 토큰을 기준으로 동작한다.
        // 즉 개발중이라도, 토큰 테스트를 할 수 있도록 한다.
        http.addFilterBefore(apiKeyChain, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }

}