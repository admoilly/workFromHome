package com.ubs.gcmm.devops.mavenapi.Controllers;

import com.ubs.gcmm.devops.mavenapi.Models.MavenExecModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Queue;

@RestController
public class MavenController {

    @Value("${spring.activemq.queue.in-use}")
    private String queueName;

    private final JmsMessagingTemplate jmsMessagingTemplate;

    private final Queue queue;

    public MavenController(JmsMessagingTemplate jmsMessagingTemplate, Queue queue) {
        this.jmsMessagingTemplate = jmsMessagingTemplate;
        this.queue = queue;
    }

    @PostMapping("/mvn")
    public void executeMavenCommand(@RequestParam String _pomPath, @RequestParam String _mavenCommand, @RequestParam(required = false) String _settings, @RequestParam(required = false) String _additionalParameters) {
        MavenExecModel execModel = new MavenExecModel(_pomPath, _settings, _additionalParameters, _mavenCommand);
        this.jmsMessagingTemplate.convertAndSend(this.queue, execModel);
    }
}
