package fucan.entity.response;

import java.util.List;

public class FilterResultResponse {

    private int id;
    private String mode;
    private List<FilterResultResponseElmt> results;

    public FilterResultResponse() {}

    public FilterResultResponse(int id, String mode, List<FilterResultResponseElmt> results) {
        this.id = id;
        this.mode = mode;
        this.results = results;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public List<FilterResultResponseElmt> getResults() {
        return results;
    }

    public void setResults(List<FilterResultResponseElmt> results) {
        this.results = results;
    }
}
