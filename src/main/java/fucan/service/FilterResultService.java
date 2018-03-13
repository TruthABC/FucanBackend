package fucan.service;

import fucan.entity.mapping.Case;
import fucan.entity.mapping.Thumb;
import fucan.entity.response.CommonResponse;
import fucan.entity.response.FilterResultResponse;
import fucan.entity.response.FilterResultResponseElmt;
import fucan.entity.response.ThumbElmt;
import fucan.mapper.CaseMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FilterResultService {

    private final CaseMapper caseMapper;

    public FilterResultService(CaseMapper caseMapper) {
        this.caseMapper = caseMapper;
    }

    public FilterResultResponse getFilterResult(String id) {
        FilterResultResponse response = null;

        //转换id的类型，非数字，即没有对应的病例
        int id_int;
        try {
            id_int = Integer.parseInt(id);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            return null;
        }

        Case cas = caseMapper.getCaseById(id_int);

        //分支：根据id未检索到病例
        if (cas == null) {
            return null;
        }

        //分支：根据id成功检索到病例
        try {
            List<FilterResultResponseElmt> resList = new ArrayList<FilterResultResponseElmt>();
            FilterResultResponseElmt res1 = new FilterResultResponseElmt("溃疡", 0, 0, new ArrayList<ThumbElmt>());
            FilterResultResponseElmt res2 = new FilterResultResponseElmt("正常", 0, 0, new ArrayList<ThumbElmt>());
            resList.add(res1);
            resList.add(res2);
            List<Thumb> thumbs = cas.getThumbs();
            for (Thumb t: thumbs) {
                if (t.getCategory().equals("溃疡")) {
                    res1.getThumbs().add(new ThumbElmt(t.getId(), t.getUrl(), t.getTime().getTime(), t.isTag()));
                } else {
                    res2.getThumbs().add(new ThumbElmt(t.getId(), t.getUrl(), t.getTime().getTime(), t.isTag()));
                }
            }
            response = new FilterResultResponse(cas.getId(), resList);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        return response;
    }

}
