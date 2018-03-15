package fucan.service;

import fucan.Global;
import fucan.entity.mapping.Case;
import fucan.entity.mapping.Thumb;
import fucan.entity.response.CaseResponseElmt;
import fucan.entity.response.CommonResponse;
import fucan.mapper.CaseMapper;
import fucan.mapper.ThumbMapper;
import fucan.processor.FilterProcessor;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.util.*;

@Service
public class CaseService {

    private final FilterProcessor filterProcessor;
    private final CaseMapper caseMapper;

    public CaseService(FilterProcessor filterProcessor, CaseMapper caseMapper) {
        this.filterProcessor = filterProcessor;
        this.caseMapper = caseMapper;
    }

    //1.获取所有病例
    public List<CaseResponseElmt> getCase() {
        List<CaseResponseElmt> cases = new ArrayList<CaseResponseElmt>();

        List<Case> caseList = caseMapper.getCase();
        for (Case cas: caseList) {
            CaseResponseElmt caseResponseElmt = new CaseResponseElmt();
            caseResponseElmt.setId(cas.getId());
            caseResponseElmt.setName(cas.getName());
            caseResponseElmt.setTime(cas.getTime().getTime());
            caseResponseElmt.setThumbUrl(cas.getThumbUrl());
            caseResponseElmt.setState(cas.getState());
            caseResponseElmt.setProgress(cas.getProgress());
            caseResponseElmt.setTotalCount(cas.getTotalCount());
            caseResponseElmt.setPositiveCount(cas.getPositiveCount());
            caseResponseElmt.setNegativeCount(cas.getNegativeCount());
            cases.add(caseResponseElmt);
        }

        return cases;
    }

    //2.开始筛查
    public CommonResponse doFilter(String id) {
        CommonResponse response = new CommonResponse(0,"");
        CommonResponse noCaseResponse = new CommonResponse(-1,"根据id未检索到病例");
        CommonResponse filterErrorResponse = new CommonResponse(-2,"筛查过程出现错误");

        //转换id的类型，非数字，即没有对应的病例
        int id_int;
        try {
            id_int = Integer.parseInt(id);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            return noCaseResponse;
        }

        Case cas = caseMapper.getCaseById(id_int);

        //分支：根据id未检索到病例
        if (cas == null) {
            return noCaseResponse;
        }

        //分支：根据id成功检索到病例
        try {
            filterProcessor.updateOnFilterStart(cas);
            filterProcessor.processFilter(cas);
            filterProcessor.updateOnFilterEnd(cas);
        } catch (Exception ex) {
            ex.printStackTrace();
            return filterErrorResponse;
        }

        return response;
    }

}
