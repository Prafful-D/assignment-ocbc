package com.ocbc.assignment.config;

/**
 * @author PraffulD
 */
//@Configuration
//@EnableWebSecurity
//public class SecurityConfiguration {
//
//    //@Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf().ignoringAntMatchers("/h2/**",
//                "/error",
//                "/favicon.ico",
//                "/**/*.png",
//                "/**/*.gif",
//                "/**/*.svg",
//                "/**/*.jpg",
//                "/**/*.html",
//                "/**/*.css",
//                "/**/*.js",
//                "/*.jsp",
//                "/h2-console/**");
//        http.headers().frameOptions().sameOrigin();
//        http.authorizeRequests()
//                .antMatchers("/", "/home").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login").permitAll()
//                .and()
//                .logout().permitAll();
//        return http.build();
//    }
//
//    //@Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("user")
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }
//}