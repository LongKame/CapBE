package com.example.JWTSecure;
import com.example.JWTSecure.domain.Role;
import com.example.JWTSecure.domain.User;
import com.example.JWTSecure.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@Configuration
@EnableWebMvc
@EnableScheduling
public class JwtSecureApplication {

	public static void main(String[] args) {SpringApplication.run(JwtSecureApplication.class, args);}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:8070");
			}
		};
	}

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
		@Bean
		CommandLineRunner run(UserService userService){
		return args -> {
//			userService.saveRole(new Role(null,"ROLE_USER"));
//			userService.saveRole(new Role(null,"ROLE_ADMIN"));
//
//			userService.saveUser(new User("LongKame","Nguyen Thanh Long","123456","longkame@gmail.com","0971858758","Thai Binh",true));
//			userService.saveUser(new User("CongKame","Vu Hoang Cong","123456","congbinh@gmail.com","0971858757","Ha Noi",true));

		};
	}
}
