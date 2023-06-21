package com.goit.hibernate.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity
@Table(name = "planet")
public class Planet {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;
}
