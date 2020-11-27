package edu.nwpu.web;

import edu.nwpu.common.Execution;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class IndexController {

    @GetMapping("/")
    public ResponseEntity<String> home() {
        return new ResponseEntity<>("Welcome to werewolf simulator! Call /bootstrap to start a session", HttpStatus.OK);
    }

    @GetMapping("/bootstrap")
    public ResponseEntity bootstrap() {
        Execution execution = Execution.initial();
        execution.proceed();
        return new ResponseEntity<>(execution, HttpStatus.OK);
    }
}
