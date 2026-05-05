package spring_lab3_notifications.service;

import org.springframework.stereotype.Service;

@Service("custom")
public class TelegramService implements MessageService{
    @Override
    public void sendMessage(String message, String recipient) {
        System.out.println("TG to " + recipient + ": " + message);
    }
}
