package az.my.datareport.service;

import az.my.datareport.config.ConfigLoader;
import org.springframework.stereotype.Service;

@Service
public class ConfigService {

    public boolean sendConfigStr(String json) {
        ConfigLoader loader = new ConfigLoader();
        return loader.loadConfig(json);
    }

}
