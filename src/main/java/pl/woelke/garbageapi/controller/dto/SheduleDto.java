package pl.woelke.garbageapi.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SheduleDto {

    private Long id;
    private String month;
    private String days;

}
