package spring_lab3_notifications.mapper;

import org.springframework.stereotype.Component;
import spring_lab3_notifications.model.dto.NotificationDto;
import spring_lab3_notifications.model.entity.Notification;

@Component
public class NotificationMapper {

    public NotificationDto toDto(Notification notification) {
        if (notification == null) {
            return null;
        }

        return NotificationDto.builder()
                .title(notification.getTitle())
                .message(notification.getMessage())
                .channel(notification.getChannel())
                .status(notification.getStatus())
                .createdAt(notification.getCreatedAt())
                .sentAt(notification.getSentAt())
                .recipientId(notification.getRecipient().getId())
                .build();
    }

    public Notification toEntity(NotificationDto dto) {
        if (dto == null) {
            return null;
        }

        Notification notification = new Notification();
        notification.setTitle(dto.getTitle());
        notification.setMessage(dto.getMessage());
        notification.setChannel(dto.getChannel());
        notification.setStatus(dto.getStatus());
        notification.setCreatedAt(dto.getCreatedAt());
        notification.setSentAt(dto.getSentAt());

        return notification;
    }
}