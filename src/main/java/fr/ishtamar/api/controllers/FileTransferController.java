package fr.ishtamar.api.controllers;

import fr.ishtamar.api.exceptionhandler.GenericNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileTransferController {
    @Value("${fr.ishtamar.starter.files-upload}")
    private String filesUpload;

    @Operation(summary = "upload the jpeg image and add it to directory",responses={
            @ApiResponse(responseCode="200", description = "Image successfully uploaded"),
            @ApiResponse(responseCode="404", description = "Access unauthorized")
    })
    @ResponseBody
    @GetMapping(value="/{fileCode}", produces = MediaType.IMAGE_JPEG_VALUE)
    @Secured("ROLE_USER")
    public byte[] getJpegImage(@PathVariable("fileCode") @Parameter(description = "the filecode of the image") final String fileCode) throws IOException, GenericNotFoundException {
        File file;
        try {
            file=ResourceUtils.getFile(filesUpload + "/" + fileCode);
            return FileUtils.readFileToByteArray(file);
        } catch (Exception e) {
            throw new GenericNotFoundException();
        }
    }
}
