package top.wdahe.common.config.springDoc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;




@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI foodOPenAPI() {
        return new OpenAPI()
                .info(new Info().title("foodAPI").description("王大合餐饮小程序项目").version("v1.0.0"));
    }

//    @Bean
//    public GroupedOpenApi appAPI() {
//        return GroupedOpenApi.builder().group("food_app").pathsToMatch("/api-app/**").build();
//    }
//
//    @Bean
//    public GroupedOpenApi systemAPI() {
//        return GroupedOpenApi.builder().group("food_system").pathsToMatch("/**").build();
//    }
}
