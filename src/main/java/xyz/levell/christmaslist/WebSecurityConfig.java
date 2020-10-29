package xyz.levell.christmaslist;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/css/**", "/js/**", "/h2-console/**", "/image/**", "/font/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();

        http.csrf().disable();
        http.headers().frameOptions().disable();

    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails ryanLogin =
                User.withDefaultPasswordEncoder()
                        .username("Ryan")
                        .password("password")
                        .roles("USER")
                        .build();

        UserDetails beccaLogin =
                User.withDefaultPasswordEncoder()
                        .username("Becca")
                        .password("password")
                        .roles("USER")
                        .build();
        UserDetails heatherLogin =
                User.withDefaultPasswordEncoder()
                        .username("Heather")
                        .password("password")
                        .roles("USER")
                        .build();
        UserDetails debLogin =
                User.withDefaultPasswordEncoder()
                        .username("Deb")
                        .password("password")
                        .roles("USER")
                        .build();
        UserDetails mikeLogin =
                User.withDefaultPasswordEncoder()
                        .username("Mike")
                        .password("password")
                        .roles("USER")
                        .build();
        UserDetails jesseLogin =
                User.withDefaultPasswordEncoder()
                        .username("Jesse")
                        .password("password")
                        .roles("USER")
                        .build();


        return new InMemoryUserDetailsManager(ryanLogin, beccaLogin, jesseLogin, mikeLogin, debLogin, heatherLogin);
    }
}