package com.onsemi.cdars.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@PropertySource("classpath:ldap.properties")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private Environment env;

    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                //                .jdbcAuthentication()
                //                .dataSource(dataSource)
                //                .usersByUsernameQuery(getUserQuery())
                //                .passwordEncoder(new BCryptPasswordEncoder())
                //                .authoritiesByUsernameQuery(getAuthoritiesQuery());

                //Config LDAP Login
                //                .ldapAuthentication()
                //                //Config Onsemi
                //                .userDnPatterns("cn={0},ou=seremban,ou=onsemi")
                //                //Config Local Development
                //                //                .userDnPatterns("cn={0},ou=Users")
                //                .contextSource()
                //                //Config Onsemi
                //                .url("ldap://edir.onsemi.com:389/o=ondex");
                //        //Config Local Development
                ////                .url("ldap://192.168.84.20:389/dc=edir,dc=onsemi,dc=com");

                //Config LDAP Login
                .ldapAuthentication()
                .userDnPatterns(env.getProperty("ldap.userDnPatterns"))
                .contextSource()
                .url(env.getProperty("ldap.url"));
    }

    private String getUserQuery() {
        return "SELECT login_id AS username, password AS password, is_active AS enabled "
                + "FROM cdars_user "
                + "WHERE login_id = ?";
    }

    private String getAuthoritiesQuery() {
        return "SELECT login_id AS username, group_id AS authority "
                + "FROM cdars_user "
                + "WHERE login_id = ?";
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/error/**").permitAll()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/logout/**").permitAll()
                //.antMatchers("/register/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/")
                .failureUrl("/error")
                .permitAll()
                .defaultSuccessUrl("/")
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .permitAll()
                .logoutSuccessUrl("/");
    }

}
