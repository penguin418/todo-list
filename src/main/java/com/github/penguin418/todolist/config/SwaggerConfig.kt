package com.github.penguin418.todolist.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean

open class SwaggerConfig {
    @Bean
    open fun customOpenAPI(): OpenAPI {
        return OpenAPI()
                .components(Components())
                .info(Info())
    }
}
