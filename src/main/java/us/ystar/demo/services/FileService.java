package us.ystar.demo.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import us.ystar.demo.exceptions.PitchFileStorageException;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService {

    public String uploadFile(MultipartFile file) {
        try {
            Path copyLocation = Paths.get("./uploads/" + StringUtils.cleanPath(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
            return copyLocation.toAbsolutePath().toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new PitchFileStorageException("Could not save the file " + file.getOriginalFilename() + "");
        }
    }

}
