package fr.ishtamar.api.rentals;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "the mapped to Dto rental model")
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
