package app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.http.HttpMethod;

import app.service.UserService;

@Configuration
@EnableWebSecurity
@ComponentScan
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests().antMatchers("/login", "/favicon.ico", "/**/*.html", "/**/*.css", "/**/*.js", 
            "/images/**", "/fonts/**", "/swagger**", "/api/**", "/cadastro/**", "/salvarCadastro").permitAll() 
            .and()
            .csrf().disable()
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, "/api/**").permitAll()
            .antMatchers(HttpMethod.PUT, "/api/**").permitAll()
            .antMatchers(HttpMethod.DELETE, "/api/**").permitAll()
            .antMatchers("/users/**").hasAuthority("ADMIN")
            .antMatchers("/processos/**").hasAuthority("TRIAD")
            .antMatchers("/pareceres/**").hasAuthority("FINAL")
            .anyRequest().authenticated()
            .and()
            .httpBasic()
            .and()
            .formLogin()
            .loginPage("/login")
            .defaultSuccessUrl("/")
            .permitAll()
            .and()
            .logout()
            .permitAll()
            .and();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
