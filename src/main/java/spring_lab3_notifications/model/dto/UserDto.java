package spring_lab3_notifications.model.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    @NotNull(message = "Имя не должно быть пустым")
    @Size(max = 100, message = "Имя не должно быть длиннее 100 символов")
    private String name;

    @NotNull(message = "Email обязателен")
    @Email(message = "Некорректный формат email")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Email не соответствует шаблону")
    private String email;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Номер телефона должен содержать 10-15 цифр и может начинаться с +")
    private String phone;

    private String deviceToken;
    private String telegramChatId;
    private LocalDateTime createdAt;
}