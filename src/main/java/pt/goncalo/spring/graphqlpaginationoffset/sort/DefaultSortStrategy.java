package pt.goncalo.spring.graphqlpaginationoffset.sort;

import graphql.schema.DataFetchingEnvironment;
import org.springframework.data.domain.Sort;
import org.springframework.graphql.data.query.AbstractSortStrategy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultSortStrategy extends AbstractSortStrategy {
    @Override
    protected List<String> getProperties(DataFetchingEnvironment environment) {
        return List.of(environment.getArgumentOrDefault("sortBy", ""));
    }

    @Override
    protected Sort.Direction getDirection(DataFetchingEnvironment environment) {
        return Sort.Direction.fromOptionalString(environment.getArgument("sortDirection")).get();
    }
}
