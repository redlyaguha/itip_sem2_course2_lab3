package spring_lab3_notifications.repository;

import spring_lab3_notifications.model.entity.Notification;
import spring_lab3_notifications.model.enums.NotificationChannel;
import spring_lab3_notifications.model.enums.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByStatus(NotificationStatus status);
    List<Notification> findByChannel(NotificationChannel channel);
    List<Notification> findByRecipientId(Long recipientId);
    List<Notification> findByStatusAndChannel(NotificationStatus status, NotificationChannel channel);
    List<Notification> findAllByOrderByCreatedAtDesc();
    List<Notification> findByStatusOrderByCreatedAtDesc(NotificationStatus status);
    @Query("""
select n
from Notification n
where n.status = :status
 and n.channel = :channel
""")
    List<Notification> findByStatusAndChannelCustom(@Param("status")
                                                    NotificationStatus status, NotificationChannel channel);

    @Query(value = """
           SELECT * 
           FROM notifications 
           WHERE status = :status 
           AND channel = :channel
           """,
            nativeQuery = true)
    List<Notification> findNativeByStatusAndChannel(
            @Param("status") String status,
            @Param("channel") String channel
    );
    @Query("SELECT n FROM Notification n WHERE n.recipient.id = :recipientId AND n.status = :status")
    List<Notification> findByRecipientIdAndStatus(
            @Param("recipientId") Long recipientId,
            @Param("status") NotificationStatus status
    );
}

