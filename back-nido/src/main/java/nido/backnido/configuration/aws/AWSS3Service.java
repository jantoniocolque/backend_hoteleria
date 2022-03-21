package nido.backnido.configuration.aws;

import org.springframework.web.multipart.MultipartFile;

public interface AWSS3Service {

    String uploadFile(MultipartFile multipartFile);
}