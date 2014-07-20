package nicusan.devzone.fu.presentation;

import nicusan.devzone.fu.service.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by ro1v02n2 on 18.07.2014.
 */
@Controller
public class FileController {

    @Autowired
    private ContentRepository contentRepository;

    @RequestMapping(value = "/files/{name}", method = RequestMethod.POST)
    public void uploadOneFile(@PathVariable("name") String name, @RequestParam(value = "caption", required = false) String caption, @RequestParam(value = "altTag", required = false) String altTag, @RequestPart(value = "file") MultipartFile file, HttpServletResponse response) throws IOException {
        try (InputStream fileIs = file.getInputStream()) {
            Properties meta = new Properties();
            if (caption != null) {
                meta.put("caption", caption);
            }
            if (altTag != null) {
                meta.put("altTag", altTag);
            }
            contentRepository.store(fileIs, name, meta);

            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
