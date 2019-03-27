package googleGuavaAndApacheCollections;

public class ExceptionHandler {
    /*
     метод log(Exception e) будет выводить краткое описание исключения.
     */
    public static  void log(Exception e){
          googleGuavaAndApacheCollections.Helper.printMessage(e.getMessage());
    }
}
