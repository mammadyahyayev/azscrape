package az.my.datareport.config;

public class ConfigDataValidator {

    //TODO: Create a class called ValidationResult, it holds messages, errors, isValid (bool element)
    // also create ValidationException and wrapped it by ConfigFileException

    /*
       TODO: check required fields, if there is a problem throw an exception.
        Check their values.
        If everything is okay return true and wait for the action from user (generate report file)
    */

    private final ConfigLoader.TempConfig tempConfig;

    public ConfigDataValidator(ConfigLoader.TempConfig tempConfig) {
        this.tempConfig = tempConfig;
    }

    public boolean validate() {
        return validateFilePart(tempConfig) && validateDataPart(tempConfig);
    }

    private boolean validateFilePart(ConfigLoader.TempConfig fileConfig) {
        String exportedFileName = fileConfig.getExportedFileName();
        String exportedFileType = fileConfig.getExportedFileType();
        String exportedFileTypeExtension = fileConfig.getExportedFileTypeExtension();

        if (exportedFileName == null || exportedFileName.isEmpty() || exportedFileName.isBlank()) {
            return false;
        }

        if (exportedFileType == null || exportedFileType.isEmpty() || exportedFileType.isBlank()) {
            return false;
        }

        if (exportedFileTypeExtension == null || exportedFileTypeExtension.isEmpty() || exportedFileTypeExtension.isBlank()) {
            return false;
        }

        return true;
    }

    private boolean validateDataPart(ConfigLoader.TempConfig dataConfig) {
        String url = dataConfig.getData().getUrl();
        ConfigLoader.TempData data = dataConfig.getData();

        if (url == null || url.isEmpty() || url.isBlank()) {
            return false;
        }

        if (data == null) {
            return false;
        }

        ConfigLoader.TempDataElement element = data.getElement();

        if (element == null) {
            return false;
        }

        return true;
    }


}
