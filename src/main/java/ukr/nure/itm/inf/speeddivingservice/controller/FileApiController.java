package ukr.nure.itm.inf.speeddivingservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RequestMapping(value = "/file")
public interface FileApiController {

    @RequestMapping(value = "/upload", method = POST)
    String uploadFile(@RequestParam("file") MultipartFile file);
}
