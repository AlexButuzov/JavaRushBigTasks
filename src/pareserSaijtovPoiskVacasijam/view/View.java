package   pareserSaijtovPoiskVacasijam.view;

import   pareserSaijtovPoiskVacasijam.Controller;
import   pareserSaijtovPoiskVacasijam.vo.Vacancy;

import java.util.List;

public interface View {
    void update(List<Vacancy> vacancies);
    void setController(Controller controller);

}
