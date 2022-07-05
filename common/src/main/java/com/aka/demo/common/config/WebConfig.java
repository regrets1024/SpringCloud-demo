package com.aka.demo.common.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.Validation;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(0, new MappingJackson2HttpMessageConverter());
        WebMvcConfigurer.super.extendMessageConverters(converters);
    }

    /**
     * 因为@Configuration注解会对修饰的类进行CGLIB增强，会跳过static修饰的方法增强处理，不会导致业务bean的提前初始化
     */
    @Bean
    public static javax.validation.Validator validator() {
        // 有一个参数出错即返回
        return Validation.byProvider(HibernateValidator.class).configure()
                .failFast(true)
                .buildValidatorFactory().getValidator();
    }

    /**
     * RequestParam方式的校验
     */
    @Bean
    public static MethodValidationPostProcessor methodValidationPostProcessor() {
        MethodValidationPostProcessor methodValidationPostProcessor = new MethodValidationPostProcessor();
        methodValidationPostProcessor.setValidator(validator());
        return methodValidationPostProcessor;
    }

    @Override
    public org.springframework.validation.Validator getValidator() {
        return new SpringValidatorAdapter(validator());
    }

}
