package fr.chatop.api.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max=63)
    private String name;

    @NotNull
    private Float surface;

    @NotNull
    private Float price;

    private String picture;

    @Size(max=1000)
    private String description;

    @ManyToOne
    @JoinColumn(name="owner_id",referencedColumnName = "id")
    private UserInfo owner;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;


}
