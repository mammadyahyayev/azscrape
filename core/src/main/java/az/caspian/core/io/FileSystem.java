package az.caspian.core.io;

import java.io.File;
import java.nio.file.Path;

/**
 * A general interface for file system.
 */
public interface FileSystem {

    /**
     * Creates a new file in File System. If there is a file
     * with given path, then it will throw {@link java.nio.file.FileAlreadyExistsException}.
     *
     * @param path a path of file
     * @return a {@code File}
     * @see java.nio.file.FileAlreadyExistsException
     */
    File createFile(Path path);

    /**
     * Creates a new file in File System. If there is a file
     * with given path, then it will return existent File.
     *
     * @param path a path of file
     * @return a {@code File}
     */
    File createFileIfNotExist(Path path);

    /**
     * Deletes file from File System if it exists.
     *
     * @param path a {@code Path}
     * @return {@code true} if file is deleted successfully, otherwise {@code false}
     */
    boolean deleteFile(Path path);

    /**
     * Checks whether file exists.
     *
     * @param path a path of File
     * @return {@code true} if file exists, otherwise {@code false}
     */
    boolean isFileExist(Path path);

    /**
     * Gets a {@code File} object with given path
     *
     * @param path a path of File
     * @return a {@code File}
     */
    File getFile(Path path);

    /**
     * Creates directory if directory exists already,
     * then will throw {@link java.nio.file.FileAlreadyExistsException}.
     *
     * @param path a path of directory
     * @return a {@code File}
     * @see java.nio.file.FileAlreadyExistsException
     */
    File createDirectory(Path path);

    /**
     * Creates directory if not exist.
     *
     * @param path a path of directory
     * @return a {@code File}
     */
    File createDirectoryIfNotExist(Path path);
}
