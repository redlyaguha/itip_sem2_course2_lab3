package spring_lab3_notifications.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import spring_lab3_notifications.mapper.NotificationMapper;
import spring_lab3_notifications.model.dto.NotificationDto;
import spring_lab3_notifications.model.entity.Notification;
import spring_lab3_notifications.model.enums.NotificationChannel;
import spring_lab3_notifications.model.enums.NotificationStatus;
import spring_lab3_notifications.service.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final NotificationMapper notificationMapper;

    @PostMapping("/add")
    public NotificationDto createNotification(@RequestBody @Valid NotificationDto request) {
        Notification response = notificationService.createNotification(request);
        return notificationMapper.toDto(response);
    }

    @GetMapping("/all")
    public List<NotificationDto> getAllNotifications() {
        return notificationService.getAllNotifications().stream()
                .map(notificationMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public NotificationDto getNotificationById(@PathVariable Long id) {
        Notification response = notificationService.getNotificationById(id);
        return notificationMapper.toDto(response);
    }

    @PutMapping("/{id}")
    public NotificationDto updateNotification(@PathVariable Long id,
                                              @RequestBody @Valid NotificationDto request) {
        Notification response = notificationService.updateNotification(id, request);
        return notificationMapper.toDto(response);
    }

    @DeleteMapping("/{id}")
    public String deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return "Уведомление удалено";
    }

    @GetMapping("/status/{status}")
    public List<NotificationDto> getByStatus(@PathVariable NotificationStatus status) {
        return notificationService.getNotificationsByStatus(status).stream()
                .map(notificationMapper::toDto)
                .toList();
    }

    @GetMapping("/channel/{channel}")
    public List<NotificationDto> getByChannel(@PathVariable NotificationChannel channel) {
        return notificationService.getNotificationsByChannel(channel).stream()
                .map(notificationMapper::toDto)
                .toList();
    }

    @GetMapping("/recipient/{recipientId}")
    public List<NotificationDto> getByRecipientId(@PathVariable Long recipientId) {
        return notificationService.getNotificationsByRecipientId(recipientId).stream()
                .map(notificationMapper::toDto)
                .toList();
    }

    @GetMapping("/filter")
    public List<NotificationDto> getByStatusAndChannel(
            @RequestParam NotificationStatus status,
            @RequestParam NotificationChannel channel) {
        return notificationService.getNotificationsByStatusAndChannel(status, channel).stream()
                .map(notificationMapper::toDto)
                .toList();
    }

    @GetMapping("/sorted")
    public List<NotificationDto> getAllSortedByDateDesc() {
        return notificationService.getAllNotificationsSortedByDateDesc().stream()
                .map(notificationMapper::toDto)
                .toList();
    }

    @GetMapping("/status/{status}/sorted")
    public List<NotificationDto> getByStatusSortedByDateDesc(@PathVariable NotificationStatus status) {
        return notificationService.getNotificationsByStatusSortedByDateDesc(status).stream()
                .map(notificationMapper::toDto)
                .toList();
    }
    @GetMapping("/recipient/{recipientId}/status/{status}")
    public List<NotificationDto> getByRecipientIdAndStatus(
            @PathVariable Long recipientId,
            @PathVariable NotificationStatus status) {
        return notificationService.getNotificationsByRecipientIdAndStatus(recipientId, status).stream()
                .map(notificationMapper::toDto)
                .toList();
    }

    @PatchMapping("/{id}/send")
    public NotificationDto markAsSent(@PathVariable Long id) {
        Notification response = notificationService.markAsSent(id);
        return notificationMapper.toDto(response);
    }
}