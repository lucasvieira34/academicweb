package br.com.academic.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.com.academic.service.ImplementsUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private ImplementsUserDetailsService userDetailsService;
	
	@Autowired
	AuthenticationSuccessHandler successHandler;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
			.csrf().disable()
			.authorizeRequests()
				.antMatchers(
						"/forgot-password**", 
						"/reset-password**",
						"/ativarCadastro**",
						"/aluno/imagem/",
						"/login**",
						"/").permitAll()
				//PERMISSÕES ALUNO
				.antMatchers(HttpMethod.GET, "/aluno/disciplinas").hasRole("ALUNO")
				//PERMISSÕES PROFESSOR
				.antMatchers(HttpMethod.GET, "/professor/**").hasRole("PROFESSOR")
				.antMatchers(HttpMethod.GET, "/").hasRole("PROFESSOR")
				//PERMISSÕES SECRETARIA
				.antMatchers("/secretaria/**").hasRole("SECRETARIA")
				.antMatchers("/resources/**").permitAll()
				.anyRequest().authenticated()
			.and()
				.formLogin().permitAll()
					.loginPage("/login")
						.successHandler(successHandler).permitAll()
			.and()
				.logout()
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        			.logoutSuccessUrl("/login?logout").permitAll();

	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception{
		web.ignoring().antMatchers("/materialize/**", "/css/**", "/fonts/**", "/img/**", "/js/**", "/vendor/**");
	}

}
