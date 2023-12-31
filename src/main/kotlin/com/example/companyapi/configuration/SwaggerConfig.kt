package com.example.companyapi.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@EnableSwagger2
class SwaggerConfig : WebMvcConfigurer{
  @Bean
  fun api(): Docket {
    return Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.example.companyapi.controller"))
        .paths(PathSelectors.any())
        .build()
  }

  override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
    // Enabling swagger-ui part for visual documentation
    registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/")
    registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/")
  }
}