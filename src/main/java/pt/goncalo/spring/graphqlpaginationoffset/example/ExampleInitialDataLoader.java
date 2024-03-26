package pt.goncalo.spring.graphqlpaginationoffset.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Component
public class ExampleInitialDataLoader {

    /**
     * Just to push some example data into the database
     *
     * @param exampleRepository
     * @return
     */
    @Bean
    public CommandLineRunner loadInitialData(ExampleRepository exampleRepository) {
        return (a) -> {
            exampleRepository.deleteAll();
            var values = IntStream.range('a', 'z').mapToObj(i -> new Example(String.valueOf((char) i))).toList();
            exampleRepository.saveAll(values);
        };

    }
}
