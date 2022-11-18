package pl.woelke.garbageapi.controller.dto;

import pl.woelke.garbageapi.model.Shedule;

import java.util.List;
import java.util.stream.Collectors;

public class SheduleDtoMapper {

    public static List<SheduleDto> mapToSheduleDtos(List<Shedule> shedules) {
        return shedules.stream()
                .map(shedule -> mapToSheduleDto(shedule))
                .collect(Collectors.toList());
    }

    private static SheduleDto mapToSheduleDto(Shedule shedule) {
        return SheduleDto.builder()
                .id(shedule.getId())
                .month(shedule.getMonth())
                .days(shedule.getDays())
                .build();
    }
}
