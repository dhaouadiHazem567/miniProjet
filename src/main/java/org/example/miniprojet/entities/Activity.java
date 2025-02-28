package org.example.miniprojet.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.miniprojet.enums.TypeActivity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Activity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Temporal(TemporalType.DATE)
    Date date;
    @Enumerated(EnumType.STRING)
    TypeActivity typeActivity;
    String subject;
    String notes;
    String documentPath;

    @ManyToMany
    @JoinTable(name = "contacts_activities",
            joinColumns = @JoinColumn(name = "id_activity"),
            inverseJoinColumns = @JoinColumn(name = "id_contact"))
    Set<Contact> contacts = new HashSet<>();

}
