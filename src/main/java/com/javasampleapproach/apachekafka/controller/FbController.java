package com.javasampleapproach.apachekafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javasampleapproach.apachekafka.services.KafkaProducer;
import com.javasampleapproach.apachekafka.storage.MessageStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value="/")
public class FbController {
	
	@Autowired
	KafkaProducer producer;
	
	@Autowired
	MessageStorage storage;
        
        @Value("${verify_token}")
        String VERIFY_TOKEN = "";
        
	// verify token
	@GetMapping(value="/")   // hub.
	public String verifyToken(
                @RequestParam("hub.mode") String mode,
                @RequestParam("hub.verify_token") String verify_token,
                @RequestParam("hub.challenge") String challenge) {
            
            
            verify_token = (verify_token != null) ? verify_token : "";
            challenge = (challenge != null) ? challenge : "";
            mode = (mode != null) ? mode : "";
            
            if (verify_token.equals (VERIFY_TOKEN)) {
                return challenge;
            }
            
            return "";
	}
        
        @PostMapping(value="/")
	public String producer(@RequestBody String body){
            System.out.println(body);
            producer.send(body);
            return "Done";
	}
	@GetMapping(value="/consumer")
	public String getAllRecievedMessage(){
            String messages = storage.toString();
            storage.clear();
            return messages;
	}
}
