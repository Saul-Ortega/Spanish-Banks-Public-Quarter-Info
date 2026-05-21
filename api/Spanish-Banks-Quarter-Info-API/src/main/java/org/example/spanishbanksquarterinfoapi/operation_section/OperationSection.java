package org.example.spanishbanksquarterinfoapi.operation_section;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.example.spanishbanksquarterinfoapi.operation.Operation;
import org.example.spanishbanksquarterinfoapi.operation_subsection.OperationSubsection;

import java.util.List;

@Entity
@Table(name = "operation_sections")
@Data
public class OperationSection {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String section;
    @ManyToOne
    @JoinColumn(name = "operation_id")
    private Operation operation;
    @JsonIgnore
    @OneToMany(mappedBy = "operationSection")
    private List<OperationSubsection> operationSubsections;
}
