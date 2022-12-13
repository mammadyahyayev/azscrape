package az.my.datareport.model.enumeration;

public enum FileExtension {
    XLSX, TXT, JSON;

    public static boolean contains(String extension) {
        if (extension == null || extension.isEmpty())
            return false;

        for (FileExtension fileExtension : values()) {
            if (fileExtension.name().equals(extension.toUpperCase())) {
                return true;
            }
        }

        return false;
    }
}
