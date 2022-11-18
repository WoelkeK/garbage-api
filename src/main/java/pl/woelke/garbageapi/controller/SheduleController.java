package pl.woelke.garbageapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.woelke.garbageapi.controller.dto.SheduleDto;
import pl.woelke.garbageapi.controller.dto.SheduleDtoMapper;
import pl.woelke.garbageapi.model.Shedule;
import pl.woelke.garbageapi.service.SheduleService;


import java.util.List;
import java.util.logging.Logger;

@RestController
@RequiredArgsConstructor
public class SheduleController {

    private static Logger LOGGER = Logger.getLogger(SheduleService.class.getName());
    private final SheduleService sheduleService;

    @GetMapping("/shedules")
    public List<SheduleDto> getShedules(@RequestParam(required = false) Integer page, Sort.Direction sort) {
        LOGGER.info("getShedules()");
        int pageNumber = page != null && page >= 0 ? page : 0;
        Sort.Direction sortDirection = sort != null ? sort : Sort.Direction.ASC;
        List<SheduleDto> sheduleList = SheduleDtoMapper.mapToSheduleDtos(sheduleService.getShedules(pageNumber, sortDirection));
        LOGGER.info("getShedules(...)");
        return sheduleList;
    }

    @GetMapping("/shedules/{id}")
    public Shedule getSingleShedule(@PathVariable long id) {
        LOGGER.info("getSingleShedule()");
        Shedule singleShedule = sheduleService.getSingleShedule(id);
        return singleShedule;
    }

    @PostMapping
    public Shedule addSingleShedule(@RequestBody Shedule shedule) {
        Shedule createdShedule = sheduleService.createShedule(shedule);
        return createdShedule;
    }

    @PutMapping("/shedules")
    public Shedule updateSingleShedule(@RequestBody Shedule shedule) {
        Shedule updatedShedule = sheduleService.updateShedule(shedule);
        return updatedShedule;
    }

    @DeleteMapping("/shedules/{id}")
    public void deleteShedule(@PathVariable long id) {
        sheduleService.deleteShedule(id);
    }
}
