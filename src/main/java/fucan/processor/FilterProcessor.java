package fucan.processor;

import fucan.Global;
import fucan.entity.mapping.Case;
import fucan.entity.mapping.Thumb;
import fucan.mapper.CaseMapper;
import fucan.mapper.ThumbMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.util.*;

@Component
public class FilterProcessor {

    private final CaseMapper caseMapper;
    private final ThumbMapper thumbMapper;

    public FilterProcessor(CaseMapper caseMapper, ThumbMapper thumbMapper) {
        this.caseMapper = caseMapper;
        this.thumbMapper = thumbMapper;
    }

    //1 私有函数：开始筛查之前更新数据库
    public void updateOnFilterStart(Case cas) throws Exception {
        cas.setState(1);
        cas.setProgress(0);

        String casePath = Global.DATA_ROOT_LOCAL + "/" + cas.getDirectory();
        File directory = new File(casePath);
        //分支：目录不存在
        if (!directory.exists()||!directory.isDirectory()) {
            throw new FileNotFoundException("目录不存在[" + casePath + "]");
        }
        //分支：目录存在
        int totalCount = 0;
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (!file.isDirectory()) {
                    totalCount++;
                }
            }
        }

        cas.setTotalCount(totalCount);
        caseMapper.updateCase(cas);
    }

    //2 调用算法以进行筛查
    public void processFilter(Case cas) throws Exception {
        String casePath = Global.DATA_ROOT_LOCAL + "/" + cas.getDirectory();
        String shell2 = "/home/jindiwei/caffe/venv/bin/python /home/jindiwei/Changhai/deploy_interface.py " + casePath;
        String shell4 = "/home/jindiwei/caffe/venv/bin/python /home/jindiwei/Changhai/deploy_interface_4.py " + casePath;

        //产生的resultFile应名为cas.getName() + ".txt"，与casePath文件夹同名
        File resultFile = new File(casePath + ".txt");
        try {
            if (resultFile.exists()) {
                resultFile.delete();
            }
            resultFile.createNewFile();
            Process p;//调用控制台执行shell
            switch (cas.getMode()) {
                case "2": p = Runtime.getRuntime().exec(shell2); p.waitFor(); break;
                case "4": System.out.println("[shijiantest-180316] " + shell4);
                           p = Runtime.getRuntime().exec(shell4); p.waitFor(); break;
                default: throw new Exception("invalid mode[" + cas.getMode() + "]");
            }
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
        //将阴性、阳性结果复制一下: step1: 建立文件夹(删除原有的文件夹)
        File directory = new File(casePath);
        if (!directory.exists()||!directory.isDirectory()) {
            throw new FileNotFoundException("目录不存在");
        }
        final int categoryCount = Global.getModeInt(cas.getMode());
        final String[] category;
        if (categoryCount == 2) {
            category = Global.MODE_2_CATEGORY;
        } else if (categoryCount == 4) {
            category = Global.MODE_4_CATEGORY;
        } else {
            category = new String[4];
        }
        for (int i = 0; i < categoryCount; i++) {
            File dir = new File(casePath + "_" + category[i]);
            Global.deleteDir(dir);
            dir.mkdir();
        }
        //将阴性、阳性结果复制一下: step2: 复制文件
        int judgeIndex;
        int fileIndex;
        for (judgeIndex = 0, fileIndex = 0; judgeIndex < judgeStr.length(); judgeIndex++, fileIndex++) {
            File file = fileList.get(fileIndex);
            char judgeCh = judgeStr.charAt(judgeIndex);
            String newFilePath = casePath;
            newFilePath += "_" + category[judgeCh - '0'];
            newFilePath += "/" + file.getName();
            File newFile = new File(newFilePath);
            newFile.createNewFile();
            Files.copy(file.toPath(), new FileOutputStream(newFile));
        }
    }

    //3 结束筛查之后更新数据库
    public void updateOnFilterEnd(Case cas) throws Exception {
        cas.setState(2);
        cas.setProgress(100);

        int categoryCount = Global.getModeInt(cas.getMode());
        final String[] category;
        if (categoryCount == 2) {
            category = Global.MODE_2_CATEGORY;
        } else if (categoryCount == 4) {
            category = Global.MODE_4_CATEGORY;
        } else {
            category = new String[4];
        }

        String casePath = Global.DATA_ROOT_LOCAL + "/" + cas.getDirectory();
        int positiveCount = 0;
        int negativeCount = 0;
        for (int i = 0; i < categoryCount; i++) {
            File dir = new File(casePath + "_" + category[i]);
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (!file.isDirectory()) {
                        if (i == 0) {
                            negativeCount++;
                        } else {
                            positiveCount++;
                        }
                        String url = Global.DATA_ROOT_URL + "/" + cas.getDirectory() + "/" + file.getName();
                        Thumb thumb = new Thumb(0, url, new Timestamp(new Date().getTime()), category[i], false, cas.getId());
                        List<Thumb> oldThumbs = thumbMapper.getThumbByUrl(url);
                        if (oldThumbs.size() == 0) {
                            thumbMapper.createThumb(thumb);
                        } else {
                            //delete and create
                            for (Thumb th: oldThumbs) {
                                thumbMapper.deleteThumbById(th.getId());
                            }
                            thumbMapper.createThumb(thumb);
                        }
                    }
                }
            }
        }
        cas.setPositiveCount(positiveCount);
        cas.setNegativeCount(negativeCount);
        caseMapper.updateCase(cas);
    }

}
