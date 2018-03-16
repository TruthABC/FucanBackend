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
            int categoryCount = 0;
            switch (cas.getMode()) {
                case "2": categoryCount = 2; break;
                case "4": categoryCount = 4; break;
                default: throw new Exception("invalid mode[" + cas.getMode() + "]");
            }
            String[] category = new String[4];
            if (categoryCount == 2) {
                category[0] = "阴性";
                category[1] = "阳性";
            } else if (categoryCount == 4) {
                category[0] = "正常和干扰项";
                category[1] = "凹陷型病变";
                category[2] = "隆起型病变";
                category[3] = "平坦型病变";
            }
            List<FilterResultResponseElmt> resList = new ArrayList<FilterResultResponseElmt>();
            for (int i = 0; i < categoryCount; i++) {
                FilterResultResponseElmt res1 = new FilterResultResponseElmt(category[i], 0, 0, new ArrayList<ThumbElmt>());
                resList.add(res1);
            }
            List<Thumb> thumbs = cas.getThumbs();
            for (Thumb t: thumbs) {
                for (int i = 0; i < categoryCount; i++) {
                    String cat = category[i];
                    if (t.getCategory().equals(cat)) {
                        resList.get(i).getThumbs().add(new ThumbElmt(t.getId(), t.getUrl(), t.getTime().getTime(), t.isTag()));
                        break;
                    }
                }
            }
            response = new FilterResultResponse(cas.getId(), cas.getMode(), resList);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        return response;
    }

}
