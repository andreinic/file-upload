package nicusan.devzone.fu.service;

import nicusan.devzone.fu.exception.AlreadyExistentException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by ro1v02n2 on 18.07.2014.
 */
@Service
public class FileSystemContentRepository implements ContentRepository{
    private static final String CR_ROOT = "cr";
    public static final String METTA_SUFFIX = ".properties";

    @Override
    public String store(InputStream is, String name, Properties meta) throws AlreadyExistentException, IOException {
        File rootDir = new File(CR_ROOT);
        rootDir.mkdirs();
        File targetFile = new File(rootDir, name);
        if(targetFile.exists()){
            throw new AlreadyExistentException("File " + name + " already exists on server.");
        }

        targetFile.createNewFile();
        try(FileOutputStream fos = new FileOutputStream(targetFile)){
            org.apache.commons.io.IOUtils.copy(is, fos);
        }

        if(meta != null && !meta.isEmpty()){
            File metaFile = new File(rootDir, name + METTA_SUFFIX);
            metaFile.createNewFile();

            try(FileOutputStream fos = new FileOutputStream(metaFile)){
                meta.store(fos, "");
            }
        }

        return name;
    }
}
