package rs.kunpero.vacation.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import rs.kunpero.vacation.config.filter.SlackRequestVerifierFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .mvcMatchers("/vacation/**")
                .permitAll()
                .and().csrf().disable();
    }

    @Bean
    public SlackRequestVerifierFilter slackRequestVerifierFilter() {
        return new SlackRequestVerifierFilter();
    }
    @Bean
    public FilterRegistrationBean slackRequestVerifierFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(slackRequestVerifierFilter());
        registration.addUrlPatterns("/vacation/*");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registration;
    }
}
