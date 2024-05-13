package com.example.bulkdbinsert.documentdb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Builder
@Document
public class User {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;

}
