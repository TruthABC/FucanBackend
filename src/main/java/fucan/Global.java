package fucan;

import java.io.File;

public class Global {

    public static final String DATA_ROOT_URL = "http://10.48.43.53:8080/FucanData/data";
    public static final String DATA_ROOT_LOCAL = "/home/jindiwei/Changhai/tomcat8/webapps/FucanData/WEB-INF/classes/static/data";

    public static void deleteDir(File dir) {
        if (!dir.exists()) {
            return;
        }
        if (dir.isDirectory()) {
            for (File file: dir.listFiles()) {
                deleteDir(file);
            }
            dir.delete();
        } else {
            dir.delete();
        }
    }
}
