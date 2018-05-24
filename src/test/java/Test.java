import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) {
        String s = "[ {\n" +
                "  \"jobId\" : 32233,\n" +
                "  \"name\" : \"foreachPartition at HTMonitorOperation.scala:93\",\n" +
                "  \"description\" : \"Streaming job from <a href=\\\"/streaming/batch/?id=1527065435000\\\">[output operation 1, batch time 16:50:35]</a>\",\n" +
                "  \"submissionTime\" : \"2018-05-23T08:50:35.083GMT\",\n" +
                "  \"completionTime\" : \"2018-05-23T08:50:35.118GMT\",\n" +
                "  \"stageIds\" : [ 32233 ],\n" +
                "  \"status\" : \"SUCCEEDED\",\n" +
                "  \"numTasks\" : 23,\n" +
                "  \"numActiveTasks\" : 0,\n" +
                "  \"numCompletedTasks\" : 23,\n" +
                "  \"numSkippedTasks\" : 0,\n" +
                "  \"numFailedTasks\" : 0,\n" +
                "  \"numActiveStages\" : 0,\n" +
                "  \"numCompletedStages\" : 1,\n" +
                "  \"numSkippedStages\" : 0,\n" +
                "  \"numFailedStages\" : 0\n" +
                "}, {\n" +
                "  \"jobId\" : 32232,\n" +
                "  \"name\" : \"foreachPartition at HTStateStatisticsFewerReduce.scala:132\",\n" +
                "  \"description\" : \"Streaming job from <a href=\\\"/streaming/batch/?id=1527065435000\\\">[output operation 0, batch time 16:50:35]</a>\",\n" +
                "  \"submissionTime\" : \"2018-05-23T08:50:35.015GMT\",\n" +
                "  \"completionTime\" : \"2018-05-23T08:50:35.077GMT\",\n" +
                "  \"stageIds\" : [ 32232 ],\n" +
                "  \"status\" : \"SUCCEEDED\",\n" +
                "  \"numTasks\" : 23,\n" +
                "  \"numActiveTasks\" : 0,\n" +
                "  \"numCompletedTasks\" : 23,\n" +
                "  \"numSkippedTasks\" : 0,\n" +
                "  \"numFailedTasks\" : 0,\n" +
                "  \"numActiveStages\" : 0,\n" +
                "  \"numCompletedStages\" : 1,\n" +
                "  \"numSkippedStages\" : 0,\n" +
                "  \"numFailedStages\" : 0\n" +
                "}]";
        String regex = ".*/batch/\\?id=([0-9]+).*\n.*submissionTime\"\\s:\\s\"(.+)\".*\n.*completionTime\"\\s:\\s\"(.+)\".*";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(s);
        while (m.find()){
            System.out.println(m.group(1)+"    "+m.group(2)+"        "+m.group(3));
        }
    }
}
