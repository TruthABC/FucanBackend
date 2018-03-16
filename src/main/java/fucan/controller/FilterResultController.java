package fucan.controller;

import fucan.entity.response.CommonResponse;
import fucan.entity.response.FilterResultResponse;
import fucan.entity.response.FilterResultResponseElmt;
import fucan.service.FilterResultService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@RequestMapping("/fucan")
public class FilterResultController {

    private final FilterResultService filterResultService;

    @Autowired
    public FilterResultController(FilterResultService filterResultService) {
        this.filterResultService = filterResultService;
    }

    //1.获取筛查结果
    @RequestMapping("/filter_result")
    @CrossOrigin
    public String getFilterResult(@RequestParam(value="session") String session,
                                  @RequestParam(value="id") String id) {
        JSONObject jsonRet;

        //TODO: session判定

        //获取查询解雇
        FilterResultResponse filterResultResponse = filterResultService.getFilterResult(id);

        if (filterResultResponse == null) {
            jsonRet = JSONObject.fromObject(new CommonResponse(-2,"获取筛查结果过程出错"));
            return jsonRet.toString();
        }

        jsonRet = JSONObject.fromObject(new CommonResponse(0,""));
        jsonRet.put("data", filterResultResponse);

        return jsonRet.toString();
    }

}
