package fucan.service;

import fucan.Global;
import fucan.entity.mapping.Case;
import fucan.entity.mapping.Thumb;
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
            final int categoryCount = Global.getModeInt(cas.getMode());
            final String[] category;
            if (categoryCount == 2) {
                category = Global.MODE_2_CATEGORY;
            } else if (categoryCount == 4) {
                category = Global.MODE_4_CATEGORY;
            } else {
                category = new String[4];
            }
            List<FilterResultResponseElmt> resList = new ArrayList<FilterResultResponseElmt>();
            for (int i = 0; i < categoryCount; i++) {
                FilterResultResponseElmt res = new FilterResultResponseElmt(category[i], 0, 0, new ArrayList<ThumbElmt>());
                resList.add(res);
            }
            List<Thumb> thumbs = cas.getThumbs();
            for (Thumb t: thumbs) {
                for (int i = 0; i < categoryCount; i++) {
                    String cat = category[i];
                    if (t.getCategory().equals(cat)) {
                        resList.get(i).setCount(resList.get(i).getCount() + 1);
                        int plusTag = 0;
                        if (t.isTag()) {
                            plusTag = 1;
                        }
                        resList.get(i).setTagCount(resList.get(i).getTagCount() + plusTag);
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
