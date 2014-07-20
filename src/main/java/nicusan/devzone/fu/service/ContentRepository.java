package nicusan.devzone.fu.service;

import nicusan.devzone.fu.exception.AlreadyExistentException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Abstraction for any kind of function that is able to store and persist (usually file) content. It can be anything,
 * ranging from a simple file system implementation, to a Jackrabbit client or so.
 * <p/>
 * Created by ro1v02n2 on 18.07.2014.
 */
public interface ContentRepository {
    /**
     * Stores content of a stream with the given name. Does not override the content with the same name.
     * Implementations can either rename the content and return the name; or throw an AlreadyExistentException
     *
     * @param is the content to store; the caller has to close the stream
     * @param name name to attach to the stored content on server
     * @param meta optional properties to be added next to the content
     * @return The name used to identify the stored content on server
     * @throws AlreadyExistentException if the implementation cannot support dynamic renaming of the content in case the name is already present
     * @throws java.io.IOException
     */
    String store(InputStream is, String name, Properties meta) throws AlreadyExistentException, IOException;
}
