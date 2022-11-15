package pl.woelke.garbageapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.woelke.garbageapi.model.Shedule;
import pl.woelke.garbageapi.repository.SheduleRepository;

import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class SheduleService {

    private static Logger LOGGER = Logger.getLogger(SheduleService.class.getName());
    private static final int PAGESIZE = 5;

    private final SheduleRepository sheduleRepository;

    public List<Shedule> getShedules(int page, Sort.Direction sort) {
        LOGGER.info("getShedules(" +page+ " "+sort+" )");
        List<Shedule> sheduleList = sheduleRepository.findAllShedules(PageRequest.of(page, PAGESIZE, Sort.by(sort, "id")));
        LOGGER.info("getShedules(...)" + sheduleList);
        return sheduleList;
    }

}
