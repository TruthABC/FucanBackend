package fucan.entity.response;

import java.util.ArrayList;
import java.util.List;

public class CategoryResponse {

    private List<String> categories;

    public CategoryResponse () {
        this.categories = new ArrayList<String>();
    }

    public CategoryResponse(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
