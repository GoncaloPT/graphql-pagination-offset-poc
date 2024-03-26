package pt.goncalo.spring.graphqlpaginationoffset;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.graphql.GraphQlSourceBuilderCustomizer;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.support.AnnotatedControllerConfigurer;
import org.springframework.stereotype.Controller;
import pt.goncalo.spring.graphqlpaginationoffset.example.Example;
import pt.goncalo.spring.graphqlpaginationoffset.example.ExampleRepository;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootApplication
@ConfigurationPropertiesScan("pt.goncalo.spring.graphqlpaginationoffset")
public class GraphqlPaginationOffsetApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraphqlPaginationOffsetApplication.class, args);
    }



    @Bean
    public BeanPostProcessor myProcessor(PageRequestHandlerMethodArgumentResolver pageRequestHandlerMethodArgumentResolver) {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof AnnotatedControllerConfigurer configurer) {
                    configurer.addCustomArgumentResolver(pageRequestHandlerMethodArgumentResolver);
                }
                return bean;
            }
        };
    }

    @Bean
    public GraphQlSourceBuilderCustomizer pageSupportGraphqlConfigCustomizer() {
        return schemaBuilder ->
                schemaBuilder
                        .configureTypeDefinitions(new PageTypeDefinitionConfigurer());


    }


}
