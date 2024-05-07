package fr.ishtamar.starter.rentals;

import fr.ishtamar.starter.exceptionhandler.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalServiceImpl implements RentalService {
    private final RentalRepository repository;

    public RentalServiceImpl(RentalRepository repository) {
        this.repository = repository;
    }

    public List<Rental> getAllRentals() {
        return repository.findAll();
    }

    @Override
    public Rental getRentalById(final Long id) throws EntityNotFoundException {
        return repository.findById(id)
                .orElseThrow(()->new EntityNotFoundException(Rental.class,"id",id.toString()));
    }
}
