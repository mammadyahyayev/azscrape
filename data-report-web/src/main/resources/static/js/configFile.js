class ConfigFile {
    title;
    description;
    fileType;
    fileExtension;
    url;
    dataElement;

    constructor(title, description, fileType, fileExtension, url, dataElement) {
        this.title = title;
        this.description = description;
        this.fileType = fileType;
        this.fileExtension = fileExtension;
        this.url = url;
        this.dataElement = dataElement;
    }

    load() {
        return {
            "exported_file_name": this.title,
            "exported_file_type": this.fileType,
            "exported_file_type_extension": this.fileExtension,
            "description": this.description,
            "data": {
                "url": this.url,
                "element": this.dataElement
            }
        };
    }
}