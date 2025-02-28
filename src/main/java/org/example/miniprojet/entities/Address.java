package org.example.miniprojet.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.miniprojet.enums.Country;

import java.io.Serializable;

@Embeddable

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Address implements Serializable {

    String address;
    String city;
    @Enumerated(EnumType.STRING)
    Country country;
    String state;
    int zipCode;

}
