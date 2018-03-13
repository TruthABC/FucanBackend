package fucan.entity.response;

public class ThumbElmt {

    private int id;
    private String url;
    private long time;
    private boolean tag;

    public ThumbElmt() {}

    public ThumbElmt(int id, String url, long time, boolean tag) {
        this.id = id;
        this.url = url;
        this.time = time;
        this.tag = tag;
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isTag() {
        return tag;
    }

    public void setTag(boolean tag) {
        this.tag = tag;
    }
}
