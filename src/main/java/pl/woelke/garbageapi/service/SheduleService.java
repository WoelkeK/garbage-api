package pl.woelke.garbageapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.woelke.garbageapi.model.Shedule;
import pl.woelke.garbageapi.repository.SheduleRepository;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class SheduleService {

    private static Logger LOGGER = Logger.getLogger(SheduleService.class.getName());
    private static final int PAGESIZE = 10;

    private final SheduleRepository sheduleRepository;

    public List<Shedule> getShedules(int page, Sort.Direction sort) {
        LOGGER.info("getShedules(" + page + " " + sort + " )");
        List<Shedule> sheduleList = sheduleRepository.findAllShedules(PageRequest.of(page, PAGESIZE, Sort.by(sort, "id")));
        LOGGER.info("getShedules(...)" + sheduleList);
        return sheduleList;
    }

    public Shedule getSingleShedule(Long id) {
        Optional<Shedule> singleShedule = sheduleRepository.findById(id);
        return singleShedule.orElseThrow();
    }

    public Shedule createShedule(Shedule shedule) {
        Shedule createdShedule = sheduleRepository.save(shedule);
        return createdShedule;
    }

    @Transactional
    public Shedule updateShedule(Shedule shedule) {
        Shedule editedShedule = sheduleRepository.findById(shedule.getId()).orElseThrow();
        editedShedule.setMonth(shedule.getMonth());
        editedShedule.setDays(shedule.getDays());
        Shedule updatedShedule = sheduleRepository.save(shedule);
        return shedule;
    }

    public void deleteShedule(Long id) {
        sheduleRepository.deleteById(id);
    }

}
