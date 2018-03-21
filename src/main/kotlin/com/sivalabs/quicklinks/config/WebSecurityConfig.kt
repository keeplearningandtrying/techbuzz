package com.sivalabs.quicklinks.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.config.annotation.web.builders.HttpSecurity

@Configuration
@EnableWebSecurity
class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    lateinit var userDetailsService: UserDetailsService

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Autowired
    @Throws(Exception::class)
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth
            .userDetailsService<UserDetailsService>(userDetailsService)
            .passwordEncoder(passwordEncoder())
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .authorizeRequests()
                .antMatchers("/resources/", "/webjars/", "/assets/").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/admin/").hasRole("ADMIN")
                //.anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error")
                .permitAll()
                .and()
            .logout()
                .logoutRequestMatcher(AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .permitAll()
                .and()
            .exceptionHandling()
                .accessDeniedPage("/accessDenied")
    }
}