package fucan.service;

import fucan.Global;
import fucan.entity.mapping.Case;
import fucan.entity.mapping.Thumb;
import fucan.entity.response.CaseResponseElmt;
import fucan.entity.response.CommonResponse;
import fucan.mapper.CaseMapper;
import fucan.mapper.ThumbMapper;
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

    private final CaseMapper caseMapper;
    private final ThumbMapper thumbMapper;

    public CaseService(CaseMapper caseMapper, ThumbMapper thumbMapper) {
        this.caseMapper = caseMapper;
        this.thumbMapper = thumbMapper;
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
            updateOnFilterStart(cas);
            processFilter(cas);
            updateOnFilterEnd(cas);
        } catch (Exception ex) {
            ex.printStackTrace();
            return filterErrorResponse;
        }

        return response;
    }

    //2.1.私有函数：开始筛查之前更新数据库
    private void updateOnFilterStart(Case cas) throws Exception {
        cas.setState(1);
        cas.setProgress(0);

        //TODO: 对接文件系统
        String path = Global.DATA_ROOT_LOCAL + "/" + cas.getDirectory();
        File directory = new File(path);
        //分支：目录不存在
        if (!directory.exists()||!directory.isDirectory()) {
            throw new FileNotFoundException("目录不存在[" + path + "]");
        }
        //分支：目录存在
        int totalCount = 0;
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                //TODO: 判定文件格式
                if (!file.isDirectory()) {
                    totalCount++;
                }
            }
        }

        cas.setTotalCount(totalCount);
        caseMapper.updateCase(cas);
    }

    //2.2.私有函数：结束筛查之后更新数据库
    private void updateOnFilterEnd(Case cas) throws Exception {
        cas.setState(2);
        cas.setProgress(100);

        //TODO: 对接文件系统
        String path = Global.DATA_ROOT_LOCAL + "/" + cas.getDirectory();
        File dirP = new File(path + "/positive");
        File dirN = new File(path + "/negative");
        //分支：目录不存在
        if (!dirP.exists()||!dirP.isDirectory()||!dirN.exists()||!dirN.isDirectory()) {
            throw new FileNotFoundException("目录不存在");
        }
        //分支：目录存在
        int positiveCount = 0;
        int negativeCount = 0;
        File[] files;
        files = dirP.listFiles();
        if (files != null) {
            for (File file : files) {
                //TODO: 判定文件格式
                if (!file.isDirectory()) {
                    positiveCount++;
                    String url = Global.DATA_ROOT_URL + "/" + cas.getDirectory() + "/positive/" + file.getName();
                    Thumb thumb = new Thumb(0, url, new Timestamp(new Date().getTime()), "溃疡", false, cas.getId());
                    if (thumbMapper.getThumbByUrl(url) == null)
                        thumbMapper.createThumb(thumb);
                }
            }
        }
        files = dirN.listFiles();
        if (files != null) {
            for (File file : files) {
                //TODO: 判定文件格式
                if (!file.isDirectory()) {
                    negativeCount++;
                    String url = Global.DATA_ROOT_URL + "/" + cas.getDirectory() + "/negative/" + file.getName();
                    Thumb thumb = new Thumb(0, url, new Timestamp(new Date().getTime()), "正常", false, cas.getId());
                    if (thumbMapper.getThumbByUrl(url) == null)
                        thumbMapper.createThumb(thumb);
                }
            }
        }
        cas.setPositiveCount(positiveCount);
        cas.setNegativeCount(negativeCount);
        caseMapper.updateCase(cas);
    }

    //2.3.私有函数：调用算法以进行筛查
    private void processFilter(Case cas) throws Exception {
        String casePath = Global.DATA_ROOT_LOCAL + "/" + cas.getDirectory();
        String shell = "/home/jindiwei/caffe/venv/bin/python /home/jindiwei/Changhai/deploy_interface.py /home/jindiwei/Changhai/tomcat8/webapps/FucanData/WEB-INF/classes/static/data/陆霞";
        File resultFile = new File(casePath + ".txt");
        try {
            if (resultFile.exists()) {
                resultFile.delete();
            }
            resultFile.createNewFile();
            Process p = Runtime.getRuntime().exec(shell);//调用控制台执行shell
            p.waitFor();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //读取算法结果信息
        Scanner scanner = new Scanner(resultFile);
        String judgeStr = scanner.nextLine();

        //去除文件夹，排序
        File[] files = new File(casePath).listFiles();
        List<File> fileList = new ArrayList<File>();
        fileList.addAll(Arrays.asList(files));

        //去除文件夹
        for (int i = 0; i < fileList.size(); i++) {
            if (!fileList.get(i).isDirectory()) {
                continue;
            }
            fileList.set(i, fileList.get(fileList.size() - 1));
            fileList.remove(fileList.size() - 1);
        }

        //排序
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if (o1.isDirectory() && o2.isFile())
                    return -1;
                if (o1.isFile() && o2.isDirectory())
                    return 1;
                return o1.getName().compareTo(o2.getName());
            }
        });

        //将阴性、阳性结果复制一下
        File directory = new File(casePath);
        if (!directory.exists()||!directory.isDirectory()) {
            throw new FileNotFoundException("目录不存在");
        }
        File dirP = new File(casePath + "/positive");
        File dirN = new File(casePath + "/negative");
        dirP.mkdir();
        dirN.mkdir();
        int judgeIndex = 0;
        for (int i = 0; i < fileList.size(); i++) {
            File file = fileList.get(i);
            if (!file.isDirectory()) {
                String newFilePath = casePath;
                if (judgeStr.charAt(judgeIndex) == '1') {
                    newFilePath += "/positive/" + file.getName();
                } else {
                    newFilePath += "/negative/" + file.getName();
                }
                File newFile = new File(newFilePath);
                if (!newFile.exists()) {
                    newFile.createNewFile();
                }
                Files.copy(file.toPath(), new FileOutputStream(newFile));
                judgeIndex++;
            }
        }
    }

}
