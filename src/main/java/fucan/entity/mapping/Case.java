package fucan.entity.mapping;

import java.sql.Timestamp;
import java.util.List;

public class Case {

    private int id;
    private String name;
    private String mode;
    private Timestamp time;// 毫秒
    private String thumbUrl;
    private int state;// 0-3 分别 未开始、进行中、待确认、已结束
    private int progress;// 进度 比如50%
    private int totalCount;// 影像总数
    private int positiveCount;// 阳性
    private int negativeCount;// 阴性
    private String directory; ////数据库独有：患者图像序列的目录
    private List<Thumb> thumbs;////ERM独有：对应的所有thumb

    public Case() {}

    public Case(int id, String name, String mode, Timestamp time, String thumbUrl, int state, int progress, int totalCount, int positiveCount, int negativeCount, String directory, List<Thumb> thumbs) {
        this.id = id;
        this.name = name;
        this.mode = mode;
        this.time = time;
        this.thumbUrl = thumbUrl;
        this.state = state;
        this.progress = progress;
        this.totalCount = totalCount;
        this.positiveCount = positiveCount;
        this.negativeCount = negativeCount;
        this.directory = directory;
        this.thumbs = thumbs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPositiveCount() {
        return positiveCount;
    }

    public void setPositiveCount(int positiveCount) {
        this.positiveCount = positiveCount;
    }

    public int getNegativeCount() {
        return negativeCount;
    }

    public void setNegativeCount(int negativeCount) {
        this.negativeCount = negativeCount;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public List<Thumb> getThumbs() {
        return thumbs;
    }

    public void setThumbs(List<Thumb> thumbs) {
        this.thumbs = thumbs;
    }
}
