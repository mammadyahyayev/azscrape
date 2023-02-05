package az.my.datareport.service;

import az.my.datareport.config.ConfigLoader;
import org.springframework.stereotype.Service;

@Service
public class ConfigService {

    public void sendConfigStr(String json) {
        ConfigLoader loader = new ConfigLoader();
        loader.loadConfig(json);
    }

}
