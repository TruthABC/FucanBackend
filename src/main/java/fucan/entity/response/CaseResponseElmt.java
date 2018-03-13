package fucan.entity.response;

public class CaseResponseElmt {

    private int id;
    private String name;
    private long time;// 毫秒
    private String thumbUrl;
    private int state;// 0-3 分别 未开始、进行中、待确认、已结束
    private int progress;// 进度 比如50%
    private int totalCount;// 影像总数
    private int positiveCount;// 阳性
    private int negativeCount;// 阴性

    public CaseResponseElmt() {}

    public CaseResponseElmt(int id, String name, long time, String thumbUrl, int state, int progress, int totalCount, int positiveCount, int negativeCount) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.thumbUrl = thumbUrl;
        this.state = state;
        this.progress = progress;
        this.totalCount = totalCount;
        this.positiveCount = positiveCount;
        this.negativeCount = negativeCount;
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
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
}
