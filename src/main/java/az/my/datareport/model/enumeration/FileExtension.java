package az.my.datareport.model.enumeration;

import az.my.datareport.mapper.EnumConverter;

public enum FileExtension implements EnumConverter<FileExtension> {
    XLSX("xlsx"), TXT("txt"), JSON("json");

    private final String fileExtension;

    FileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String fileType() {
        return fileExtension;
    }

    @Override
    public FileExtension convert(String str) {
        FileExtension[] extensions = FileExtension.values();
        for (FileExtension extension : extensions) {
            if (extension.fileExtension.equals(str.toLowerCase())) {
                return extension;
            }
        }

        return null;
    }
}
