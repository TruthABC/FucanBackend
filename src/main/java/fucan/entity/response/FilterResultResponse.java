package fucan.entity.response;

import java.util.List;

public class FilterResultResponse {

    private int id;
    private List<FilterResultResponseElmt> results;

    public FilterResultResponse() {}

    public FilterResultResponse(int id, List<FilterResultResponseElmt> results) {
        this.id = id;
        this.results = results;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<FilterResultResponseElmt> getResults() {
        return results;
    }

    public void setResults(List<FilterResultResponseElmt> results) {
        this.results = results;
    }
}
