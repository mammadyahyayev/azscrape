package az.my.datareport.scanner;

import az.my.datareport.config.ConfigFile;
import az.my.datareport.model.ReportFile;
import az.my.datareport.tree.DataAST;

/**
 * Scans config file
 */
public interface ConfigFileScanner {
    /**
     * Read configurations of data part, data part includes name, selector,
     * children of the element
     *
     * @param filaPath path of the config file
     * @return AST for config data
     */
    DataAST readDataConfig(String filaPath);

    /**
     * Read configurations of file part, file part includes
     * file name, type, extension and so on.
     *
     * @param configFile config file
     * @return ReportFile with file configurations
     */
    ReportFile readFileConfig(ConfigFile configFile);
}
