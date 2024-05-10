package fr.chatop.api.services.impl;

import fr.chatop.api.exceptionhandler.EntityNotFoundException;
import fr.chatop.api.util.FileUploadUtil;
import fr.chatop.api.entities.Rental;
import fr.chatop.api.requests.rentals.UpdateRentalRequest;
import fr.chatop.api.repositories.RentalRepository;
import fr.chatop.api.services.RentalService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RentalServiceImpl implements RentalService {

    private final RentalRepository repository;

    private final FileUploadUtil fileUploadUtil;

    public RentalServiceImpl(RentalRepository repository, FileUploadUtil fileUploadUtil) {
        this.repository = repository;
        this.fileUploadUtil = fileUploadUtil;
    }

    @Override
    public List<Rental> getAllRentals() {
        return repository.findAll();
    }

    @Override
    public Rental getRentalById(final Long id) throws EntityNotFoundException {
        return repository.findById(id)
                .orElseThrow(()->new EntityNotFoundException(Rental.class,"id",id.toString()));
    }

    @Override
    public String savePicture(MultipartFile multipartFile) throws IOException {
        return fileUploadUtil.saveFile(multipartFile);
    }

    @Override
    public Rental createRental(Rental rental) {
        return repository.save(rental);
    }

    @Override
    public Rental updateRental(Rental rental, UpdateRentalRequest updateRentalRequest) {

        rental.setUpdated_at(LocalDateTime.now());

        if (updateRentalRequest.getName() != null && !updateRentalRequest.getName().isEmpty()) rental.setName(updateRentalRequest.getName());
        if (updateRentalRequest.getPrice() != null) rental.setPrice(updateRentalRequest.getPrice());
        if (updateRentalRequest.getSurface() != null) rental.setSurface(updateRentalRequest.getSurface());
        if (updateRentalRequest.getDescription() != null && !updateRentalRequest.getDescription().isEmpty()) rental.setDescription(updateRentalRequest.getDescription());

        return repository.save(rental);
    }
}
