package com.javarush.task.task28.task2810.vo;

/**
 * Этот класс будет хранить данные о вакансии.
 */

public class Vacancy {
    private String title,   // название вакансии
            salary,         // зарплата
            city,           // город
            companyName,    // имя компании
            siteName,       // имя сайта
            url;            // адрес сайта

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int hashCode() {
        return new Long(this.title.hashCode()
                + this.city.hashCode()
                + this.companyName.hashCode()
                + this.salary.hashCode()
                + this.siteName.hashCode()
                + this.url.hashCode()).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vacancy) {
            Vacancy checkVacancy = (Vacancy) obj;
            if (this.title.equals(checkVacancy.getTitle())
                    && this.city.equals(checkVacancy.getCity())
                    && this.companyName.equals(checkVacancy.getCompanyName())
                    && this.salary.equals(checkVacancy.getSalary())
                    && this.siteName.equals(checkVacancy.getSiteName())
                    && this.url.equals(checkVacancy.getUrl()))
                return true;

        }
        return false;
    }
}
