package cyber.data.regulation.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @PostMapping("/adjudicate")
    public void adjudicate() {

    }

    @PostMapping("/similarPush")
    public void similarPush() {

    }

    @PostMapping("/supplyEvidence")
    public void supplyEvidence() {

    }

    @PostMapping("/computeAmount")
    public void computeAmount(){

    }

}
