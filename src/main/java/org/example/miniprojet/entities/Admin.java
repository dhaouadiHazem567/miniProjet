package org.example.miniprojet.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

import java.io.Serializable;

@Entity
@DiscriminatorValue("ADMIN")

@Getter
@Setter

public class Admin extends User implements Serializable {
}
