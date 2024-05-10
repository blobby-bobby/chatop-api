package fr.chatop.api.services;

import fr.chatop.api.exceptionhandler.EntityNotFoundException;
import fr.chatop.api.entities.Rental;
import fr.chatop.api.requests.rentals.UpdateRentalRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RentalService {

    /**
     * get all rentals from the database
     * @return all rentals
     */
    List<Rental> getAllRentals();

    /**
     * retrieve the rental from the database with the id (if it exists)
     * @param id
     * @return the rental corresponding to the id
     * @throws EntityNotFoundException if Rental id not found in database
     */
    Rental getRentalById(final Long id) throws EntityNotFoundException;

    /**
     * Save picture to local folder
     * @param multipartFile the file object
     * @return the path of the saved file
     * @throws IOException if file can't be saved
     */
    String savePicture(MultipartFile multipartFile) throws IOException;

    /**
     * Save rental to the database
     * @param rental (the rental object)
     * @return the saved rental
     */
    Rental createRental(Rental rental);

    /**
     * Update the rental in the database
     * @param rental (the current rental object)
     * @param updateRentalRequest (the object that updates a rental)
     * @return updated rental
     */
    Rental updateRental(Rental rental, UpdateRentalRequest updateRentalRequest);
    
}
