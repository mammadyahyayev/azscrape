package az.my.datareport.service;

import az.my.datareport.config.ConfigLoader;
import az.my.datareport.tree.DataAST;
import org.springframework.stereotype.Service;

@Service
public class ConfigService {

    public DataAST sendConfigStr(String json) {
        ConfigLoader loader = new ConfigLoader();
        return loader.loadConfig(json);
    }

}
