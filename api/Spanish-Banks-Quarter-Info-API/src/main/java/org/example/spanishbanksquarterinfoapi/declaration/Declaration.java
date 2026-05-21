package org.example.spanishbanksquarterinfoapi.declaration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.example.spanishbanksquarterinfoapi.bank.Bank;
import org.example.spanishbanksquarterinfoapi.operation.Operation;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "declarations")
@Data
public class Declaration {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String quarter;
    private String type;
    private Date declarationDate;
    private Date publishedDate;
    @ManyToOne
    @JoinColumn(name = "bank_id")
    private Bank bank;
    @JsonIgnore
    @OneToMany(mappedBy = "declaration")
    private List<Operation> operations;
}
