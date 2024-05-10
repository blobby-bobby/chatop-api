package fr.chatop.api.rentals;

import fr.chatop.api.exceptionhandler.EntityNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RentalService {

    List<Rental> getAllRentals();

    Rental getRentalById(final Long id) throws EntityNotFoundException;

    String savePicture(MultipartFile multipartFile) throws IOException;

    Rental createRental(Rental rental);

    Rental updateRental(Rental rental, UpdateRentalRequest updateRentalRequest);
    
}