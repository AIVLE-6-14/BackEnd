package com.example.AISafety.domain.organization;

import com.example.AISafety.domain.followup.FollowUp;
import com.example.AISafety.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "organizations")
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String number;
    private String address;

    // user와의 관계 (1:N)
    @OneToMany(mappedBy = "organization")
    private List<User> users;

    // followUp과의 관계(1:N)
    @OneToMany(mappedBy = "organization")
    private List<FollowUp> followup;
}
