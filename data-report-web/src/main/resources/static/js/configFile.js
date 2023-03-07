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
            "fileName": this.title,
            "fileType": this.fileType,
            "fileExtension": this.fileExtension,
            "description": this.description,
            "dataParts": [
                {
                    "url": this.url,
                    "elements": [
                        {
                            "name": this.dataElement.name,
                            "selector": this.dataElement.selector,
                            "subElements": this.dataElement.children,
                        }
                    ]
                }
            ]
        };
    }
}