package   task2810.model;

import   task2810.view.View;
import   task2810.vo.Vacancy;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private View view;
    private Provider[] providers;

    public Model(View view, Provider... providers) {
        if (view == null) throw new IllegalArgumentException("Viev is Null, Error in Model Constructor");
        if (providers == null)
            throw new IllegalArgumentException("Provider is Null, Error in Model Constructor");
        if ( providers.length == 0)
            throw new IllegalArgumentException("Provider is Null, Error in Model Constructor");
        this.view = view;
        this.providers = providers;
    }

    public void selectCity(String city) {
        List<Vacancy> vacanciesByAllProviders = new ArrayList<>();
        for (Provider provider: providers) {
            vacanciesByAllProviders.addAll(provider.getJavaVacancies(city));
        }
        view.update(vacanciesByAllProviders);
    }
}
