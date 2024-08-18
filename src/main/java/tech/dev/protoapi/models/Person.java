package tech.dev.protoapi.models;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Person {

    private String id;
    private String fullName;
    private Date birthDate;
    private Integer age;
}
