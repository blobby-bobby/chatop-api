package fr.chatop.api.rentals;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateRentalRequest {

    private String name;

    private Float surface;

    private Float price;

    private MultipartFile picture;

    private String description;

}
