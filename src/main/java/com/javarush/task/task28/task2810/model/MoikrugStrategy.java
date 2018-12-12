package com.javarush.task.task28.task2810.model;

import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MoikrugStrategy implements Strategy  {
    private static final String URL_FORMAT = "https://moikrug.ru/vacancies?q=java+%s&page=%d&skills[]=1012&qid=3&currency=rur"; // для выбора квалификации Junior &skills[]=1012&currency=rur";
    String url = String.format(URL_FORMAT, "Севастополь", 0);


    //String url = String.format(URL_FORMAT, "Kiev", 0);
    /**
     * Метод возвращает список вакансий согласно поисковому запросу
     *&qid=3&currency=rur
     * @param searchString поисковый запрос, пример "Москва"
     * @return список вакансий на сайте @URL_FORMAT hh.ru
     */
    @Override
    public List<Vacancy> getVacancies(String searchString) {
        List<Vacancy> vacancyList = new ArrayList<>();
        try {
            int currentPage = 0;

            while (true) {
                Document currentPageOnMoikrug = getDocument(searchString, currentPage);
                List<Element> vacanciesByElement = currentPageOnMoikrug.getElementsByAttributeValueStarting("id", "job_");
                if (vacanciesByElement.size() == 0) break;
                if (vacanciesByElement != null) {
                    for (Element element : vacanciesByElement) {
                        vacancyList.add(
                                getVacancyFromElementOnCurrentPage(element));
                    }
                }
                currentPage++;
            }
        } catch (IOException exp) {
            exp.printStackTrace();
        }


        return vacancyList;
    }

    /**
     * Метод возвращает объект типа Vacancy созданный и заполненный из текущего элемента-вакансии
     *
     * @param element текущий элемент-вакансия
     * @return объект типа Vacancy
     */
    private Vacancy getVacancyFromElementOnCurrentPage(Element element) {
        Vacancy vac = new Vacancy();
        vac.setTitle(
                element
                        .getElementsByAttributeValueContaining("class", "title").attr("title"));
        vac.setCity(
                element
                        .getElementsByAttributeValueContaining("class", "location").text());
        vac.setCompanyName(
                element
                        .getElementsByAttributeValue("class", "company_name").text());
        vac.setSiteName(URL_FORMAT);

        String urlPage = "https://moikrug.ru" +element
                .getElementsByAttributeValueContaining("class", "title").first()
                .getElementsByTag("a")
                .attr("href");
        vac.setUrl(urlPage);

        String salary = element.getElementsByAttributeValueContaining("class", "salary").text();
        vac.setSalary(salary);
        return vac;
    }

    /**
     * Метод возвращает объект типа Document @org.jsoup.nodes.Document,
     * как результат чтения вебстриницы,
     * открытой согласно поисковому запросу и указанному номеру страницы результата
     *
     * @param searchString поисковый запрос
     * @param page         номер поисковой страницы результата
     * @return объект типа Document, содержащий страницу - результат поиска
     * @throws IOException
     */
    protected Document getDocument(String searchString, int page)
            throws IOException {
        String url = String.format(URL_FORMAT, searchString, page);
        return Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 6.1; r…) Gecko/20100101 Firefox/63.0")
                .referrer("")
                .get();
    }
}
