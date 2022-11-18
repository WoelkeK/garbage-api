package pl.woelke.garbageapi.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.woelke.garbageapi.model.Shedule;


import java.util.List;
@Repository
public interface SheduleRepository extends JpaRepository<Shedule, Long> {

    @Query("select p from Shedule p")
    List<Shedule> findAllShedules(Pageable page);
}