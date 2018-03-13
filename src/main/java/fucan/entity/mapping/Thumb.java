package fucan.entity.mapping;

import java.sql.Timestamp;

public class Thumb {

    private int id;
    private String url;
    private Timestamp time;
    private String category;
    private boolean tag;
    private int caseId;

    public Thumb() {}

    public Thumb(int id, String url, Timestamp time, String category, boolean tag, int caseId) {
        this.id = id;
        this.url = url;
        this.time = time;
        this.category = category;
        this.tag = tag;
        this.caseId = caseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isTag() {
        return tag;
    }

    public void setTag(boolean tag) {
        this.tag = tag;
    }

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }
}
