package spring_lab3_notifications.service;


import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import spring_lab3_notifications.model.dto.NotificationDto;
import spring_lab3_notifications.model.entity.Notification;
import spring_lab3_notifications.model.entity.User;
import spring_lab3_notifications.model.enums.NotificationChannel;
import spring_lab3_notifications.model.enums.NotificationStatus;
import spring_lab3_notifications.repository.NotificationRepository;
import spring_lab3_notifications.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    @Transactional
    public Notification createNotification(NotificationDto request) {
        User user = userRepository.findById(request.getRecipientId())
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        Notification notification = new Notification();
        notification.setTitle(request.getTitle());
        notification.setMessage(request.getMessage());
        notification.setChannel(request.getChannel());
        notification.setStatus(NotificationStatus.CREATED);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setRecipient(user);

        notificationRepository.save(notification);

        if (true) {
            throw new RuntimeException("Искусственная ошибка");
        }

        return notification;
    }
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }
    public Notification getNotificationById(Long id) {
        return notificationRepository.findById(id).orElseThrow();
    }
    public Notification updateNotification(Long id, NotificationDto request) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Уведомление не найдено"));

        notification.setTitle(request.getTitle());
        notification.setMessage(request.getMessage());
        notification.setChannel(request.getChannel());

        NotificationStatus newStatus = request.getStatus();
        notification.setStatus(newStatus);

        if (newStatus == NotificationStatus.SENT && notification.getSentAt() == null) {
            notification.setSentAt(LocalDateTime.now());
        }
        else if (newStatus != NotificationStatus.SENT) {
            notification.setSentAt(null);
        }

        return notificationRepository.save(notification);
    }

    @Transactional
    public Notification markAsSent(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Уведомление не найдено"));

        notification.setStatus(NotificationStatus.SENT);
        notification.setSentAt(LocalDateTime.now());

        return notificationRepository.save(notification);
    }

    public void deleteNotification(Long id) {
        Notification notification =
                notificationRepository.findById(id).orElseThrow();
        notificationRepository.delete(notification);
    }
    public List<Notification> getNotificationsByStatus(NotificationStatus
                                                               status) {
        return notificationRepository.findByStatus(status);
    }
    public List<Notification> getNotificationsByChannel(NotificationChannel
                                                                channel) {
        return notificationRepository.findByChannel(channel);
    }
    public List<Notification> getNotificationsByRecipientId(Long
                                                                    recipientId) {
        return notificationRepository.findByRecipientId(recipientId);
    }

    public List<Notification> getNotificationsByStatusAndChannel(NotificationStatus status, NotificationChannel channel) {
        return notificationRepository.findByStatusAndChannel(status, channel);
    }

    public List<Notification> getAllNotificationsSortedByDateDesc() {
        return notificationRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<Notification> getNotificationsByStatusSortedByDateDesc(NotificationStatus status) {
        return notificationRepository.findByStatusOrderByCreatedAtDesc(status);
    }

    public List<Notification> getNotificationsByRecipientIdAndStatus(Long recipientId, NotificationStatus status) {
        return notificationRepository.findByRecipientIdAndStatus(recipientId, status);
    }
}