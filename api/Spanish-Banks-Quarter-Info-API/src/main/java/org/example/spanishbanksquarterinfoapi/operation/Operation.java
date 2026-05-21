package org.example.spanishbanksquarterinfoapi.operation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.example.spanishbanksquarterinfoapi.declaration.Declaration;
import org.example.spanishbanksquarterinfoapi.operation_section.OperationSection;

import java.util.List;

@Entity
@Table(name = "operations")
@Data
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String type;
    @ManyToOne
    @JoinColumn(name = "declaration_id")
    private Declaration declaration;
    @JsonIgnore
    @OneToMany(mappedBy = "operation")
    private List<OperationSection> operationSections;
}
