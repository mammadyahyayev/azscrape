package az.my.datareport.scanner;

import az.my.datareport.ast.DataAST;
import az.my.datareport.model.ReportFile;
import az.my.datareport.parser.ConfigFile;

public interface ConfigFileScanner {
    DataAST readDataConfig(String filaPath);

    ReportFile readFileConfig(ConfigFile configFile);
}
