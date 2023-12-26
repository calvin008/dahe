package top.wdahe.food_system.common.mvc;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import top.wdahe.food_system.common.interceptor.SystemApiInterceptor;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    @Resource
    private SystemApiInterceptor sysInterceptor;

    @Override
    public void  addInterceptors(InterceptorRegistry registry) {
        WebMvcConfigurer.super.addInterceptors(registry);
        registry.addInterceptor(this.sysInterceptor).addPathPatterns("/**");
    }

//    //生成环境配置跨域
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 添加映射路径
        registry.addMapping("/**")
                // 允许跨域的域名或 IP，星号代表允许所有

//                .allowedOriginPatterns("*.wdahe.top")

                .allowedOriginPatterns("*")
                // 请求允许的方法，如：POST, GET, PUT, DELETE等
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                // 预检间隔时间
                .maxAge(168000)
                // 允许头部设置
                .allowedHeaders("*");

    }
}
