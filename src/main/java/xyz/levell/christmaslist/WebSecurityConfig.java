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

import java.util.Arrays;
import java.util.List;

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
                .permitAll()

                .and()
                .rememberMe().key("uniqueAndSecret")
        ;


        http.csrf().disable();
        http.headers().frameOptions().disable();

    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        List<String> users = Arrays.asList("Ryan", "Becca", "Deb", "Mike", "Heather", "Jesse", "Jeremy",
                "Mary","Kaylee","Justice", "Brian", "Emily", "Harper", "Landry", "Phinley", "James",
                "Michael", "Jessica", "Channing", "Pearson", "Andy", "Lorena", "Nyomie", "Brett", "Hannah",
                "Scott", "Robin", "Ron", "Laura", "Kris", "Lyon", "Bob", "Bill", "Barry", "Jarek", "Brooks");

        InMemoryUserDetailsManager service = new InMemoryUserDetailsManager();

        for(int i = 0; i < users.size(); i++){
            UserDetails details =
                User.withDefaultPasswordEncoder()
                .username(users.get(i))
                .password("password")
                .roles("USER")
                .build();
            service.createUser(details);
        }
//        UserDetails ryanLogin =
//                User.withDefaultPasswordEncoder()
//                        .username("Ryan")
//                        .password("password")
//                        .roles("USER")
//                        .build();
//
//        UserDetails beccaLogin =
//                User.withDefaultPasswordEncoder()
//                        .username("Becca")
//                        .password("password")
//                        .roles("USER")
//                        .build();
//        UserDetails heatherLogin =
//                User.withDefaultPasswordEncoder()
//                        .username("Heather")
//                        .password("password")
//                        .roles("USER")
//                        .build();
//        UserDetails debLogin =
//                User.withDefaultPasswordEncoder()
//                        .username("Deb")
//                        .password("password")
//                        .roles("USER")
//                        .build();
//        UserDetails mikeLogin =
//                User.withDefaultPasswordEncoder()
//                        .username("Mike")
//                        .password("password")
//                        .roles("USER")
//                        .build();
//        UserDetails jesseLogin =
//                User.withDefaultPasswordEncoder()
//                        .username("Jesse")
//                        .password("password")
//                        .roles("USER")
//                        .build();
//        UserDetails brianLogin =
//                User.withDefaultPasswordEncoder()
//                        .username("Brian")
//                        .password("password")
//                        .roles("USER")
//                        .build();
//        UserDetails michaelLogin =
//                User.withDefaultPasswordEncoder()
//                        .username("Michael")
//                        .password("password")
//                        .roles("USER")
//                        .build();
//        UserDetails andyLogin =
//                User.withDefaultPasswordEncoder()
//                        .username("Andy")
//                        .password("password")
//                        .roles("USER")
//                        .build();
//        UserDetails jeremyLogin =
//                User.withDefaultPasswordEncoder()
//                        .username("Jeremy")
//                        .password("password")
//                        .roles("USER")
//                        .build();
//        UserDetails jamesLogin =
//                User.withDefaultPasswordEncoder()
//                        .username("James")
//                        .password("password")
//                        .roles("USER")
//                        .build();


            return service;
//        return new InMemoryUserDetailsManager(ryanLogin, beccaLogin, jesseLogin, mikeLogin, debLogin, heatherLogin, brianLogin, michaelLogin, andyLogin, jeremyLogin, jamesLogin);
    }
}
