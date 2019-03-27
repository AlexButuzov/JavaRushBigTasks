package   pareserSaijtovPoiskVacasijam.model;

import   pareserSaijtovPoiskVacasijam.vo.Vacancy;

import java.util.List;

public interface Strategy {
    List<Vacancy> getVacancies(String searchString);
}
