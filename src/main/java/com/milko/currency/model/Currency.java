package com.milko.currency.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("currencies")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Currency {
    @Id
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String code;
    private String description;
    private Boolean active;
}
