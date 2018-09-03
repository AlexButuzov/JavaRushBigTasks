package com.javarush.task.task33.task3310;

public class ExceptionHandler {
    /*
     метод log(Exception e) будет выводить краткое описание исключения.
     */
    public static  void log(Exception e){
        Helper.printMessage(e.getMessage());
    }
}
