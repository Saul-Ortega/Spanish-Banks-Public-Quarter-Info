package org.example.spanishbanksquarterinfoapi.operation_section;

import jakarta.persistence.*;
import lombok.Data;
import org.example.spanishbanksquarterinfoapi.operation.Operation;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Entity
@Table(name = "operation_sections")
@Data
public class OperationSection {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String type;
    private Boolean practiced;
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> data;
    @ManyToOne
    @JoinColumn(name = "operation_id")
    private Operation operation;
}
