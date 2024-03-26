# Purpose
The porpose of this repo is to both serve as an incubator for an offset pagination library and to showcase its usage

# Offset Pagination

## Ask for a page

Page arguments ( like pageNumber and pageSize ) are passed as arguments to the query. 
Those are then resolved by the library and the result is returned as a PageRequest object.

In the query one uses:
```graphql
query {
    examplePaged(pageNumber:5 , pageSize: 10){
        
    }
}
```

Then in the controller one receives:
```java
@QueryMapping("examplePaged")
public Page<Example> paged(PageRequest pageRequest) {
    return exampleRepository.findPage(
            pageRequest
    );
}
```


## Implementation

For this to be possible, I implemented a TypeDefinitionConfigurer( PageTypeDefinitionConfigurer ) that adds the 
necessary fields to the schema.
Also, A HandlerMethodArgumentResolver ( PageRequestHandlerMethodArgumentResolver ) was implemented to resolve the
PageRequest object from the query arguments ( pageSize and pageNumber).


