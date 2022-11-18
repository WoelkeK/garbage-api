package pl.woelke.garbageapi.controller.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
@Builder
public class PostDto {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime created;
}
