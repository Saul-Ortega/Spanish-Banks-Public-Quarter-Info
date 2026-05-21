package org.example.spanishbanksquarterinfoapi.bank;

import jakarta.persistence.*;
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
    private String entity;
    private String denomination;
    @OneToMany(mappedBy = "bank")
    private List<Declaration> declarations;
}
