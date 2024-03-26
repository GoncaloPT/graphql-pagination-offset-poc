package pt.goncalo.spring.graphqlpaginationoffset.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ExampleController {
    final ExampleRepository exampleRepository;
    private static final Logger log = LoggerFactory.getLogger(ExampleController.class);

    public ExampleController(ExampleRepository exampleRepository) {
        this.exampleRepository = exampleRepository;
    }

    @QueryMapping("examplePaged")
    public Page<Example> paged(PageRequest pageRequest /**, Sort sort)**/) {
        log.debug("pageRequest = " + pageRequest);
        return exampleRepository.findAll(pageRequest);
    }

    @QueryMapping("examples")
    public List<Example> list() {
        return exampleRepository.findAll();
    }
}
