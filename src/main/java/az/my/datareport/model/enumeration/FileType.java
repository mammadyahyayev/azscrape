package az.my.datareport.model.enumeration;

import az.my.datareport.mapper.EnumConverter;

public enum FileType implements EnumConverter<FileType> {
    CSV("csv"), TEXT("text"), JSON("json");

    private final String fileType;

    FileType(String fileType) {
        this.fileType = fileType;
    }

    public String fileType() {
        return fileType;
    }

    @Override
    public FileType convert(String str) {
        FileType[] values = FileType.values();
        for (FileType value : values) {
            if (value.fileType.equals(str.toLowerCase())) {
                return value;
            }
        }
        return null;
    }
}
