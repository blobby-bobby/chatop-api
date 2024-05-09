package fr.ishtamar.starter.messages;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {
    private Long id;

    @NotNull
    private Long rental_id;

    @NotNull
    private Long user_id;

    @NotNull
    @Size(max = 1000)
    private String message;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;
}
