package com.example.demo.integration;

import com.example.demo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.GenericHandler;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.support.GenericMessage;

import java.io.File;
import java.io.IOException;

@Configuration
public class FileIntegrationFlow {

    @Autowired
    private FileService fileService;

    @Bean
    @InboundChannelAdapter(value = "fileInputChannel", poller = @Poller(fixedDelay = "5000"))
    public FileReadingMessageSource fileReadingMessageSource() {
        FileReadingMessageSource source = new FileReadingMessageSource();
        source.setDirectory(new File("C:/SITA_TEST_TASK/IN"));
        return source;
    }

    @Bean
    public MessageChannel fileInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public GenericHandler<Message<File>> fileHandler() {
        return (message, headers) -> {
            try {
                fileService.processFiles();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        };
    }
}
