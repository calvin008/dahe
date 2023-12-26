package top.wdahe.food_system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"top.wdahe"})
@MapperScan("top.wdahe.**.mapper")
public class FoodSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodSystemApplication.class, args);
    }

}
