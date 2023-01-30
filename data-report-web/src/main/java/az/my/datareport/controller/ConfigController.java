package az.my.datareport.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ConfigController {

    @PostMapping("/config/send")
    public ResponseEntity<String> postData(@RequestBody String json) {
        System.out.println(json);
        return new ResponseEntity<>("The config file received", HttpStatus.OK);
    }

}
