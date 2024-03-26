package pt.goncalo.spring.graphqlpaginationoffset;

import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.HandlerMethodArgumentResolver;
import org.springframework.graphql.data.query.SortStrategy;
import org.springframework.stereotype.Component;

/**
 * {@link HandlerMethodArgumentResolver} that resolves {@link Pageable} argument by looking
 * for pageNumber and pageSize arguments in the {@link DataFetchingEnvironment}, which holds the actual graphql
 * query parameters
 */
@Component
public class PageRequestHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private final GraphqlPageRequestConfiguration pageRequestDefaultConfiguration;
    private final SortStrategy sortStrategy;
    private final Logger log = LoggerFactory.getLogger(PageRequestHandlerMethodArgumentResolver.class);

    public PageRequestHandlerMethodArgumentResolver(GraphqlPageRequestConfiguration pageRequestDefaultConfiguration, SortStrategy sortStrategy) {
        this.pageRequestDefaultConfiguration = pageRequestDefaultConfiguration;
        this.sortStrategy = sortStrategy;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Pageable.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, DataFetchingEnvironment environment) throws Exception {
        var pageNumber = environment.getArgumentOrDefault("pageNumber", pageRequestDefaultConfiguration.getPageNumber());
        var pageSize = environment.getArgumentOrDefault("pageSize", pageRequestDefaultConfiguration.getPageSize());

        log.debug("sortStrategy is " + sortStrategy);
        var sort = sortStrategy.extract(environment);
        log.debug("found sort is " + sort);
        if (sort == null) {
            return PageRequest.of(pageNumber, pageSize);
        }
        return PageRequest.of(pageNumber, pageSize, sort);
    }


}
