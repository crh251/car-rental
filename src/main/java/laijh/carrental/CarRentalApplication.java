package laijh.carrental;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author laijh
 */
@SpringBootApplication
@MapperScan("laijh.carrental.dao")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class CarRentalApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarRentalApplication.class, args);
    }

}
