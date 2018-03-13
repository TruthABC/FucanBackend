package fucan.controller;

import fucan.entity.response.CategoryResponse;
import fucan.entity.response.CommonResponse;
import fucan.entity.response.SessionResponse;
import fucan.service.CategoryService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@RequestMapping("/fucan")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //1.得到所有分类名
    @RequestMapping("/category")
    public String getCategory(@RequestParam(value="session") String session) {

        JSONObject jsonRet;

        //TODO: session判定

        //取得分类名
        List<String> categories = categoryService.getCategory();

        jsonRet = JSONObject.fromObject(new CommonResponse(0,""));
        jsonRet.put("data", new CategoryResponse(categories));

        return jsonRet.toString();
    }

}
