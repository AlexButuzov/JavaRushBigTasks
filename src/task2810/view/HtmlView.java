package   task2810.view;

import   task2810.Controller;
import   task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


import java.io.*;

import java.util.List;

public class HtmlView implements View {
    Controller controller;

    //filePath, содержит относительный путь к vacancies.html.
    //Путь должен быть относительно корня проекта.
    //Формирмируем путь динамически используя this.getClass().getPackage() и разделитель "/".
    private final String filePath =
            "./4.JavaCollections/src/"
                    + this.getClass().getPackage().getName().replace('.', '/')
                    + "/vacancies.html";

    /**
     * Метод  запускает: формирование  и сохраниение нового файла вакансий,
     * так же обрабатывает ошибки исполения.
     * Согласно условию 13 при ошибка происходи печать стек-трейса на экран
     *
     * @param vacancies Список вакансий
     */
    @Override
    public void update(List<Vacancy> vacancies) {

        String updatedContentOnFileVacancies = getUpdatedFileContent(vacancies);
        updateFile(updatedContentOnFileVacancies);

    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void userCitySelectEmulationMethod(String city) {
        controller.onCitySelect(city);
    }

    /**
     * Метод возвращает объект типа Document @org.jsoup.nodes.Document,
     * как результат чтения схраненной локально вебстриницы,
     * кодировка
     *
     * @return
     * @throws IOException
     */
    protected Document getDocument() throws IOException {
        File in = new File(filePath);
        return Jsoup.parse(in, "UTF-8");
    }

    /*
     * Метод формирует новое тело файла vacancies.html,
     * которое будет содержать вакансии, вывод в ввиде строки
     * условие 15
     *
     * @return тстрока, содержащая тело файла
     */
    private String getUpdatedFileContent(List<Vacancy> vacancies) {
        Document document = null;
        try {
            document = getDocument();
            Element templateVacancyElement = document
                    .getElementsByClass("template").first();
            Element patternVacancyElement = templateVacancyElement.clone();
            patternVacancyElement
                    .removeClass("template")
                    .removeAttr("style");
            document
                    .getElementsByAttributeValueEnding("class", "vacancy")
                    .remove();

            for (Vacancy vacancy : vacancies) {
                Element currentElement = patternVacancyElement.clone();

                currentElement
                        .getElementsByClass("city").first()
                        .text(vacancy.getCity());
                currentElement
                        .getElementsByClass("companyName").first()
                        .text(vacancy.getCompanyName());
                currentElement
                        .getElementsByClass("salary").first()
                        .text(vacancy.getSalary());
                //currentElement.getElementsByAttribute("siteName").first().text(vacancy.getSiteName());

                Element link = currentElement.getElementsByTag("a").first();
                link.text(vacancy.getTitle());
                link.attr("href", vacancy.getUrl());

                templateVacancyElement.before(currentElement.outerHtml());
            }

        } catch (IOException exp) {
            exp.printStackTrace();
            return "Some exception occurred";
        }
        return document.html();
    }

    /*
     * Метод записывает в файл vacancies.html его обновленное тело
     *
     * @param vacanciesToUpdate обновленное тело  vacancies.html в формате Строки.
     */
    private void updateFile(String vacanciesToUpdate) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(vacanciesToUpdate);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
