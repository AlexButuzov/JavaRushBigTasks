package LogPareserAndSimpleQueue;

import java.nio.file.Paths;

public class Solution {
    private static String sourceAdress = (Solution.class.getProtectionDomain().getCodeSource().getLocation().getPath().substring(1) +
            Solution.class.getPackage().getName().replaceAll(".model", "") + ".logs")
            .replaceAll("[.]", "/").replace("target/classes", "src");
    public static void main(String[] args) {
        //System.out.println(sourceAdress);
        LogParser logParser = new LogParser(Paths.get(sourceAdress));
       // System.out.println(logParser.getNumberOfUniqueIPs(null, new Date()));
        //System.out.println(logParser.getUniqueIPs(null, null));
       // System.out.println(logParser.getIPsForEvent(Event.DONE_TASK, null, null));
       // System.out.println(logParser.getIPsForStatus(Status.FAILED, null, null));
       // System.out.println(logParser.getIPsForUser("Amigo",null,null));
        System.out.println(logParser.getNumberOfUserEvents("Amigo",null,null));
        System.out.println(logParser.getAllSolvedTasksAndTheirNumber(null,null));
        System.out.println(logParser.getAllDoneTasksAndTheirNumber(null,null));
        System.out.println(logParser.execute("get ip for user = \"Eduard Petrovich Morozko\" and date between \"11.12.2013 0:00:00\" and \"03.01.2014 23:59:59\""));


        System.out.println(logParser.execute("get ip for user = \"Amigo\" and date between \"11.12.2013 0:00:00\" and \"03.01.2046 23:59:59\""));
        System.out.println(logParser.execute("get ip for event = \"SOLVE_TASK\" and date between \"11.12.2013 0:00:00\" and \"03.01.2017 23:59:59\""));
        System.out.println(logParser.execute("get ip for status = \"OK\" and date between \"11.12.2013 0:00:00\" and \"03.01.2017 23:59:59\""));
        System.out.println(logParser.execute("get date for event = \"SOLVE_TASK\" and date between \"11.12.2013 0:00:00\" and \"03.01.2017 23:59:59\""));
    }
}