package fr.ishtamar.starter.rentals;

import fr.ishtamar.starter.user.UserInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Date;

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
    private UserInfo user;

    @CreatedDate
    private Date created_at=new Date();

    @UpdateTimestamp
    private Date updated_at;

}
