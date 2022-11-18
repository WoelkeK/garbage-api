package pl.woelke.garbageapi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
@Getter
@Setter
public class Shedule {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String month;
        private String days;

//        @ElementCollection
//        @CollectionTable(name = "shedule_month_mapping",
//        joinColumns = {@JoinColumn(name="month_id", referencedColumnName = "id")})
//        @MapKeyColumn(name = "day")
//        private Map<Integer, ArrayList<Integer>> sheduleDayMap;

}
