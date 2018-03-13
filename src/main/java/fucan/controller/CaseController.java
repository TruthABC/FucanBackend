package fucan.controller;

import fucan.entity.response.CaseResponse;
import fucan.entity.response.CaseResponseElmt;
import fucan.entity.response.CommonResponse;
import fucan.service.CaseService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@RequestMapping("/fucan")
public class CaseController {

    private final CaseService caseService;

    @Autowired
    public CaseController(CaseService caseService) {
        this.caseService = caseService;
    }

    //1.获取所有病例
    @RequestMapping("/case")
    public String getCase(@RequestParam(value="session") String session) {
        JSONObject jsonRet;

        //TODO: session判定

        //获取所有病例
        List<CaseResponseElmt> cases = caseService.getCase();

        jsonRet = JSONObject.fromObject(new CommonResponse(0,""));
        jsonRet.put("data", new CaseResponse(cases));

        return jsonRet.toString();
    }

    //2.开始筛查
    @RequestMapping("/filter_start")
    public String startFilter(@RequestParam(value="session") String session,
                              @RequestParam(value="id") String id) {
        JSONObject jsonRet;

        //TODO: session判定

        CommonResponse response = caseService.doFilter(id);
        jsonRet = JSONObject.fromObject(response);

        return jsonRet.toString();
    }

}
