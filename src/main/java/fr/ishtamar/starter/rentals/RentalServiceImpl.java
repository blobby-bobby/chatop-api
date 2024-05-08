package fr.ishtamar.starter.rentals;

import fr.ishtamar.starter.exceptionhandler.EntityNotFoundException;
import fr.ishtamar.starter.filetransfer.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
        return fileUploadUtil.saveFile(multipartFile.getOriginalFilename(), multipartFile);
    }

    @Override
    public Rental createRental(Rental rental) {
        return repository.save(rental);
    }

    @Override
    public Rental updateRental(Rental rental) {
        return null;
    }
}
