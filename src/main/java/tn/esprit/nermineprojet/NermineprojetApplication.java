package tn.esprit.nermineprojet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class NermineprojetApplication {

    public static void main(String[] args) {
        SpringApplication.run(NermineprojetApplication.class, args);
    }

}
