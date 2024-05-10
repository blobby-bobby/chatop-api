package fr.ishtamar.api.rentals;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateRentalRequest {
    private String name;

    private Float surface;

    private Float price;

    private String description;
}
