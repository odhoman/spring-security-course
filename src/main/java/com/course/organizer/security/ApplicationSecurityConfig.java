package com.course.organizer.security;

import static com.course.organizer.security.ApplicationUserRole.ADMIN;

import com.course.organizer.service.UserService;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    private final UserService userAuthenticationService;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder
        , UserService userAuthenticationService){
        this.passwordEncoder = passwordEncoder;
        this.userAuthenticationService = userAuthenticationService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .and()
            .authorizeRequests()
            .antMatchers("/", "/index", "/css/*", "/js/*","/login").permitAll()
            .antMatchers("/h2-console/**","/index", "/images/*").permitAll()

             //All Roles
            .antMatchers(HttpMethod.GET, "/course").hasAuthority(
            ApplicationUserPermission.COURSE_READ.getPermission())
            .antMatchers(HttpMethod.GET, "/course/read/*").hasAuthority(
            ApplicationUserPermission.COURSE_READ.getPermission())

            // Student
            .antMatchers(HttpMethod.POST, "/course/student/attend").hasRole(
            ApplicationUserRole.STUDENT.name())
            .antMatchers(HttpMethod.POST, "/course/student/leave").hasRole(
            ApplicationUserRole.STUDENT.name())

            // Teacher
            .antMatchers(HttpMethod.GET, "/course/teacher/give").hasRole(
            ApplicationUserRole.TEACHER.name())
            .antMatchers(HttpMethod.GET, "/course/teacher/stop").hasRole(
            ApplicationUserRole.TEACHER.name())

            // Admin
            .antMatchers(HttpMethod.POST, "/course/enable").hasRole(ADMIN.name())
            .antMatchers(HttpMethod.POST, "/course/disable").hasRole(ADMIN.name())
            .antMatchers(HttpMethod.POST, "/course/create").hasRole(ADMIN.name())
            .antMatchers(HttpMethod.POST, "/course/update").hasRole(ADMIN.name())
            .antMatchers(HttpMethod.POST, "/course/createOrUpdate").hasRole(ADMIN.name())
            .antMatchers(HttpMethod.GET, "/course/create").hasRole(ADMIN.name())
            .antMatchers(HttpMethod.GET, "/course/edit/*").hasRole(ADMIN.name())


                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .defaultSuccessUrl("/course", true)
                    .passwordParameter("password")
                    .usernameParameter("username")
                    .failureUrl("/login-error")
                .and()
                .rememberMe()
                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
                    .key("somethingverysecured")
                    .rememberMeParameter("remember-me")
                .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                    .logoutUrl("/logout")
//                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")) // https://docs.spring.io/spring-security/site/docs/4.2.12.RELEASE/apidocs/org/springframework/security/config/annotation/web/configurers/LogoutConfigurer.html
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID", "remember-me")
                    .logoutSuccessUrl("/login")
            .and()
            .exceptionHandling().accessDeniedPage("/accessDenied")
        .and()
            .headers().frameOptions().sameOrigin();
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userAuthenticationService).passwordEncoder(passwordEncoder);
    }

//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        UserDetails annaSmithUser = User.builder()
//                .username("adam")
//                .password(passwordEncoder.encode("p"))
//                .authorities(ApplicationUserRole.STUDENT.getGrantedAuthorities())
//                .build();
//
//        UserDetails lindaUser = User.builder()
//                .username("john")
//                .password(passwordEncoder.encode("p"))
//                .authorities(ApplicationUserRole.TEACHER.getGrantedAuthorities())
//                .build();
//
//        UserDetails tomUser = User.builder()
//                .username("kelly")
//                .password(passwordEncoder.encode("p"))
//                .authorities(ApplicationUserRole.ADMIN.getGrantedAuthorities())
//                .build();
//
//        return new InMemoryUserDetailsManager(
//                annaSmithUser,
//                lindaUser,
//                tomUser
//        );
//
//    }


}
