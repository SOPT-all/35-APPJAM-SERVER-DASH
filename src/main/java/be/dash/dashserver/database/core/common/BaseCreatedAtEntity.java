package be.dash.dashserver.database.core.common;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import lombok.Getter;

@MappedSuperclass
@Getter
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseCreatedAtEntity {

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

}
