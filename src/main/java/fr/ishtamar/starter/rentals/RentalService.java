package fr.ishtamar.starter.rentals;


import fr.ishtamar.starter.exceptionhandler.EntityNotFoundException;

public interface RentalService {
    Rental getRentalById(final Long id) throws EntityNotFoundException;
}
