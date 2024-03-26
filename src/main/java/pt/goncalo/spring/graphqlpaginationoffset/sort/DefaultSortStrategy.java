package pt.goncalo.spring.graphqlpaginationoffset.sort;

import graphql.schema.DataFetchingEnvironment;
import org.springframework.data.domain.Sort;
import org.springframework.graphql.data.query.AbstractSortStrategy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class DefaultSortStrategy extends AbstractSortStrategy {
    @Override
    protected List<String> getProperties(DataFetchingEnvironment environment) {
        var maybeSortBy = Optional.ofNullable(environment.getArgument("sortBy")).map(String::valueOf);
        return maybeSortBy
                .map(List::of)
                .orElse(List.of());


    }

    @Override
    protected Sort.Direction getDirection(DataFetchingEnvironment environment) {

        var askedDirection = environment.<String>getArgument("sortDirection");
        System.out.println("askedDirection: " + askedDirection);
        return Sort.Direction
                .fromOptionalString(askedDirection)
                .orElse(null);
    }
}
