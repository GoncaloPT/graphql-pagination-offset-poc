package pt.goncalo.spring.graphqlpaginationoffset;

import graphql.language.FieldDefinition;
import graphql.language.ImplementingTypeDefinition;
import graphql.language.ListType;
import graphql.language.NonNullType;
import graphql.language.ObjectTypeDefinition;
import graphql.language.Type;
import graphql.language.TypeName;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.TypeDefinitionConfigurer;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * {@link TypeDefinitionConfigurer} that generates "Page" types by looking for
 * fields whose type definition name ends in "Page" so they can atually be
 * considered a {@link org.springframework.data.domain.Page} type.
 */
public class PageTypeDefinitionConfigurer implements TypeDefinitionConfigurer {

    private static final TypeName STRING_TYPE = new TypeName("String");
    private static final TypeName BOOLEAN_TYPE = new TypeName("Boolean");
    private static final TypeName INT_TYPE = new TypeName("Int");

    @Override
    public void configure(TypeDefinitionRegistry registry) {
        var typeNames = findPageTypeNames(registry);
        if (!typeNames.isEmpty()) {
            typeNames.forEach(typeName -> {
                String pageTypeName = typeName + "Page";
                registry.add(ObjectTypeDefinition.newObjectTypeDefinition()
                        .name(pageTypeName)
                        .fieldDefinition(initFieldDefinition("content", new NonNullType(new ListType(new TypeName(typeName)))))
                        //TODO .fieldDefinition(initFieldDefinition("sort", new NonNullType(new TypeName(typeName))))
                        .fieldDefinition(initFieldDefinition("totalElements", new NonNullType(INT_TYPE)))
                        .fieldDefinition(initFieldDefinition("totalPages", new NonNullType(INT_TYPE)))
                        .fieldDefinition(initFieldDefinition("hasContent", new NonNullType(BOOLEAN_TYPE)))
                        .fieldDefinition(initFieldDefinition("hasNext", new NonNullType(BOOLEAN_TYPE)))
                        .fieldDefinition(initFieldDefinition("hasPrevious", new NonNullType(BOOLEAN_TYPE)))
                        .build()
                );
            });

        }


    }


    private static Set<String> findPageTypeNames(TypeDefinitionRegistry registry) {
        return Stream.concat(
                        registry.types().values().stream(),
                        registry.objectTypeExtensions().values().stream().flatMap(Collection::stream))
                .filter(definition -> definition instanceof ImplementingTypeDefinition)
                .flatMap(definition -> {
                    ImplementingTypeDefinition<?> typeDefinition = (ImplementingTypeDefinition<?>) definition;
                    return typeDefinition.getFieldDefinitions().stream()
                            .map(fieldDefinition -> {
                                Type<?> type = fieldDefinition.getType();
                                return (type instanceof NonNullType ? ((NonNullType) type).getType() : type);
                            })
                            .filter(type -> type instanceof TypeName)
                            .map(type -> ((TypeName) type).getName())
                            .filter(name -> name.endsWith("Page"))
                            .filter(name -> registry.getType(name).isEmpty())
                            .map(name -> name.substring(0, name.length() - "Page".length()));
                })
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private FieldDefinition initFieldDefinition(String name, Type<?> returnType) {
        return FieldDefinition.newFieldDefinition().name(name).type(returnType).build();
    }
}
