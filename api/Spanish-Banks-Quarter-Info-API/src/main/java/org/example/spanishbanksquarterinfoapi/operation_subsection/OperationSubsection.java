package org.example.spanishbanksquarterinfoapi.operation_subsection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.example.spanishbanksquarterinfoapi.operation_section.OperationSection;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Entity
@Table(name = "operation_subsections")
@Data
public class OperationSubsection {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String section;
    private boolean practiced;
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> data;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "operation_section_id")
    private OperationSection operationSection;
}
