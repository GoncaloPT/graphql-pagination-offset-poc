package pt.goncalo.spring.graphqlpaginationoffset;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;


@ConfigurationProperties(prefix = "pt.goncalo.spring.graphqlpaginationoffset.offsetpagination")
public final class GraphqlPageRequestConfiguration {
    private final int pageNumber;
    private final int pageSize;

    @ConstructorBinding
    public GraphqlPageRequestConfiguration(@DefaultValue("0") int pageNumber,
                                           @DefaultValue("10") int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }
}
