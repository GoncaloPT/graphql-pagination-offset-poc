package pt.goncalo.spring.graphqlpaginationoffset;

import graphql.schema.DataFetchingEnvironment;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.HandlerMethodArgumentResolver;
import org.springframework.stereotype.Component;

/**
 * {@link HandlerMethodArgumentResolver} that resolves {@link Pageable} argument by looking
 * for pageNumber and pageSize arguments in the {@link DataFetchingEnvironment}, which holds the actual graphql
 * query parameters
 */
@Component
public class PageRequestHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private final GraphqlPageRequestConfiguration pageRequestDefaultConfiguration;

    public PageRequestHandlerMethodArgumentResolver(GraphqlPageRequestConfiguration pageRequestDefaultConfiguration) {
        this.pageRequestDefaultConfiguration = pageRequestDefaultConfiguration;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Pageable.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, DataFetchingEnvironment environment) throws Exception {
        var pageNumber = environment.getArgumentOrDefault("pageNumber", pageRequestDefaultConfiguration.getPageNumber());
        var pageSize = environment.getArgumentOrDefault("pageSize", pageRequestDefaultConfiguration.getPageSize());
        return PageRequest.of(pageNumber, pageSize);
    }


}
