package az.my.datareport.model.enumeration;

public enum FileType {
    CSV, TEXT, JSON, EXCEL;

    public static boolean contains(String type) {
        if (type == null || type.isEmpty())
            return false;

        for (FileType fileType : values()) {
            if (fileType.name().equals(type.toUpperCase())) {
                return true;
            }
        }

        return false;
    }
}
