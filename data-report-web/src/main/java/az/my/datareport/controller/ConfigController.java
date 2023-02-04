package az.my.datareport.controller;

import az.my.datareport.config.ConfigurationException;
import az.my.datareport.service.ConfigService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ConfigController {

    public final ConfigService configService;

    public ConfigController(ConfigService configService) {
        this.configService = configService;
    }

    @PostMapping("/config/send")
    public ResponseEntity<String> postData(@RequestBody String json) {
        try {
            boolean isSend = configService.sendConfigStr(json);
            if (isSend) {
                return new ResponseEntity<>("Configurations received...", HttpStatus.OK);
            }
        } catch (ConfigurationException ex) {
            return new ResponseEntity<>(
                    "Exception: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR
            );
            //TODO: log and show message to end user.
            //TODO: Add logging dependency to distribution management
        }
        //TODO: create a connector class between controller and service
        // send json string to end connection, it must throw an exception if there is a problem
        // catch that exception in here and show the problem to user

        return new ResponseEntity<>("Configurations received...", HttpStatus.OK);
    }

}
