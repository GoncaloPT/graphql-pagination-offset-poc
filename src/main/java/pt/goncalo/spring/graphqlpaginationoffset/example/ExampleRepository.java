package pt.goncalo.spring.graphqlpaginationoffset.example;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.goncalo.spring.graphqlpaginationoffset.example.Example;

public interface ExampleRepository extends JpaRepository<Example, Long> {


}
