package fucan.service;

import fucan.entity.mapping.Category;
import fucan.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryService(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    //1.得到所有分类名，返回字符串列表
    public List<String> getCategory() {
        List<Category> categoryList = categoryMapper.getCategory();
        List<String> categories = new ArrayList<String>();
        for (Category cate: categoryList) {
            categories.add(cate.getName());
        }
        return categories;
    }

}
