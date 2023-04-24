package az.my.datareport.service;

import az.my.datareport.controller.vm.ConfigDataVM;
import az.my.datareport.controller.vm.ConfigFileVM;
import az.my.datareport.controller.vm.ElementVM;
import az.my.datareport.converter.StringToEnumConverter;
import az.my.datareport.model.ReportFile;
import az.my.datareport.model.enumeration.FileExtension;
import az.my.datareport.model.enumeration.FileType;
import az.my.datareport.tree.AbstractTree;
import az.my.datareport.tree.DataNode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigService {

    private ConfigFileVM configFile;

    public AbstractTree getDataPart() {
        if (configFile == null) {
            throw new IllegalArgumentException("configFile data cannot be null");
        }

        List<ConfigDataVM> dataParts = configFile.getDataParts();

        AbstractTree abstractTree = new AbstractTree();
        for (ConfigDataVM dataPart : dataParts) {
            String url = dataPart.getUrl();
            List<ElementVM> elements = dataPart.getElements();
            DataNode node = null;
            for (ElementVM element : elements) {
                node = new DataNode(element.getName(), element.getSelector());
                List<ElementVM> children = element.getSubElements();
                for (ElementVM child : children) {
                    DataNode subNode = new DataNode(child.getName(), child.getSelector());
                    //node.addSubNode(subNode);
                }
            }

            abstractTree.addNode(node);
        }

        return abstractTree;
    }

    public ReportFile getReportFilePart() {
        if (configFile == null) {
            throw new IllegalArgumentException("configFile data cannot be null");
        }

        FileType fileType = StringToEnumConverter.convert(configFile.getFileType(), FileType.class);
        FileExtension fileExtension = StringToEnumConverter.convert(configFile.getFileExtension(), FileExtension.class);

        return new ReportFile.Builder()
                .filename(configFile.getFileName())
                .fileType(fileType)
                .fileExtension(fileExtension)
                .build();
    }

    public void setConfiguration(ConfigFileVM configFile) {
        this.configFile = configFile;
    }

}
