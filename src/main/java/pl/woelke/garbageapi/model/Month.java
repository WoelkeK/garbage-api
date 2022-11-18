package pl.woelke.garbageapi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
public class Month {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int digitCode;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name="monthId", updatable = false, insertable = false)
    private List<Day> day;
}
