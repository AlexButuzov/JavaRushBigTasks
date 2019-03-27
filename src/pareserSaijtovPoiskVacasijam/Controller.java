package pareserSaijtovPoiskVacasijam;

import   pareserSaijtovPoiskVacasijam.model.Model;

public class Controller {
   private  Model model;

    public Controller(Model model) {
        if (model == null) throw new IllegalArgumentException("model == null in Controller constructor");
        this.model = model;
    }

    public void onCitySelect(String cityName) {
        model.selectCity(cityName);
    }
}
