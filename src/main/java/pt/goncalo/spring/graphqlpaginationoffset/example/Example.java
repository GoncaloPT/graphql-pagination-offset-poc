package pt.goncalo.spring.graphqlpaginationoffset.example;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Example {
    @Id
    private String charValue;

    public Example() {
    }

    public Example(String charValue) {
        this.charValue = charValue;
    }

    public String getCharValue() {
        return charValue;
    }

    public void setCharValue(String charValue) {
        this.charValue = charValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Example example = (Example) o;

        return Objects.equals(charValue, example.charValue);
    }

    @Override
    public int hashCode() {
        return charValue != null ? charValue.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Example{" +
                "charValue='" + charValue + '\'' +
                '}';
    }
}
