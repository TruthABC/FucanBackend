package fucan.entity.response;

import java.util.List;

public class FilterResultResponseElmt {

    private String category;
    private int count;
    private int tagCount;
    private List<ThumbElmt> thumbs;

    public FilterResultResponseElmt() {}

    public FilterResultResponseElmt(String category, int count, int tagCount, List<ThumbElmt> thumbs) {
        this.category = category;
        this.count = count;
        this.tagCount = tagCount;
        this.thumbs = thumbs;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTagCount() {
        return tagCount;
    }

    public void setTagCount(int tagCount) {
        this.tagCount = tagCount;
    }

    public List<ThumbElmt> getThumbs() {
        return thumbs;
    }

    public void setThumbs(List<ThumbElmt> thumbs) {
        this.thumbs = thumbs;
    }
}
