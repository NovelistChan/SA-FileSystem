package czf.nju.namenode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class NamenodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(NamenodeApplication.class, args);
    }

}
