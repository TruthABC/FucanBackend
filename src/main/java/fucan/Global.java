package fucan;

import java.io.File;

public class Global {

    public static final String DATA_ROOT_URL = "http://10.48.43.53:8080/FucanData/data";
    public static final String DATA_ROOT_LOCAL = "/home/jindiwei/Changhai/tomcat8/webapps/FucanData/WEB-INF/classes/static/data";

    public static final String[] MODE_2_CATEGORY = {"阴性", "阳性"};
    public static final String[] MODE_4_CATEGORY = {"正常和干扰项", "凹陷型病变", "隆起型病变", "平坦型病变"};

    public static int getModeInt(String mode) {
        if (mode.equals("2")) {
            return 2;
        } else if (mode.equals("4")) {
            return 4;
        }
        return 0;
    }

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
