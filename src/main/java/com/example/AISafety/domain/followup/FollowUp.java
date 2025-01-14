package com.example.AISafety.domain.followup;

import com.example.AISafety.domain.animal.Animal;
import com.example.AISafety.domain.organization.Organization;
import com.example.AISafety.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FollowUp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="animal_id", nullable = false)
    private Animal animal;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name= "organization_id", nullable = false)
    private Organization organization;

    @Column(nullable = false)
    private String status="PENDING";

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
