package fr.ishtamar.starter.rentals;

import fr.ishtamar.starter.user.UserInfo;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RentalDto {
    private Long id;

    @NotNull
    @Size(max=63)
    private String name;

    @NotNull
    @Min(value=0)
    private Float surface;

    @NotNull
    @Min(value=0)
    private Float price;

    private String picture;

    @Size(max=1000)
    private String description;

    @NotNull
    private Long owner_id;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;

}
