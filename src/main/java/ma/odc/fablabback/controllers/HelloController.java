package ma.odc.fablabback.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HelloController {
    @GetMapping("/")
    public ResponseEntity<String>hello(){
        return ResponseEntity.ok("<h1>Hello From FabLab BackEnd</h1>");
    }

}
