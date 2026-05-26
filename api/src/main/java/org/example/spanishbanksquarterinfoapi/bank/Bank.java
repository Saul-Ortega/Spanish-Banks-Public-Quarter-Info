package org.example.spanishbanksquarterinfoapi.bank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.example.spanishbanksquarterinfoapi.declaration.Declaration;

import java.util.List;

@Entity
@Table(name = "banks")
@Data
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Size(min = 4)
    private String entity;
    @Column(unique = true)
    private String denomination;
    @JsonIgnore
    @OneToMany(mappedBy = "bank")
    private List<Declaration> declarations;
}
