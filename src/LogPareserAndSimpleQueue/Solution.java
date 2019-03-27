package LogPareserAndSimpleQueue;

import java.nio.file.Paths;

public class Solution {
    public static void main(String[] args) {
        LogParser logParser = new LogParser(Paths.get("C:\\_nout\\java\\_projects\\JavaRushTasks\\4.JavaCollections\\src\\com\\javarush\\task\\task39\\LogPareserAndSimpleQueue\\logs"));
       // System.out.println(logParser.getNumberOfUniqueIPs(null, new Date()));
        //System.out.println(logParser.getUniqueIPs(null, null));
       // System.out.println(logParser.getIPsForEvent(Event.DONE_TASK, null, null));
       // System.out.println(logParser.getIPsForStatus(Status.FAILED, null, null));
       // System.out.println(logParser.getIPsForUser("Amigo",null,null));
        //System.out.println(logParser.getNumberOfUserEvents("Amigo",null,null));
        //System.out.println(logParser.getAllSolvedTasksAndTheirNumber(null,null));
        //System.out.println(logParser.getAllDoneTasksAndTheirNumber(null,null));
       // System.out.println(logParser.execute("get ip for user = \"Eduard Petrovich Morozko\" and date between \"11.12.2013 0:00:00\" and \"03.01.2014 23:59:59\""));


        System.out.println(logParser.execute("get ip for user = \"Amigo\" and date between \"11.12.2013 0:00:00\" and \"03.01.2046 23:59:59\""));
        System.out.println(logParser.execute("get ip for event = \"SOLVE_TASK\" and date between \"11.12.2013 0:00:00\" and \"03.01.2017 23:59:59\""));
        System.out.println(logParser.execute("get ip for status = \"OK\" and date between \"11.12.2013 0:00:00\" and \"03.01.2017 23:59:59\""));
        System.out.println(logParser.execute("get date for event = \"SOLVE_TASK\" and date between \"11.12.2013 0:00:00\" and \"03.01.2017 23:59:59\""));
    }
}