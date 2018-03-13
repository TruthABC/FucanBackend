package fucan.entity.response;

import java.util.ArrayList;
import java.util.List;

public class CaseResponse {

    private List<CaseResponseElmt> cases;

    public CaseResponse() {
        cases = new ArrayList<CaseResponseElmt>();
    }

    public CaseResponse(List<CaseResponseElmt> cases) {
        this.cases = cases;
    }

    public List<CaseResponseElmt> getCases() {
        return cases;
    }

    public void setCases(List<CaseResponseElmt> cases) {
        this.cases = cases;
    }
}
