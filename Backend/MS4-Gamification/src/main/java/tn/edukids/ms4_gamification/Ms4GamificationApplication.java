package tn.edukids.ms4_gamification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Ms4GamificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(Ms4GamificationApplication.class, args);
	}

}
