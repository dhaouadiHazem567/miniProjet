package org.example.miniprojet.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.miniprojet.enums.JobTitle;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("CONTACT")

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Contact extends User implements Serializable {

    Long phone;
    @Enumerated(EnumType.STRING)
    JobTitle jobTitle;
    String company;
    @Embedded
    Address address;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contact_owner_id")
            @JsonIgnore
    User contactOwner;

    @ManyToMany(mappedBy = "contacts")
            @JsonIgnore
    Set<Activity> activities = new HashSet<>();
}
