package fucan.mapper;

import fucan.entity.mapping.Thumb;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Mapper
@Repository
public interface ThumbMapper {

    //1.根据url查询thumb
    @Select(" SELECT *" +
            " FROM `thumb`" +
            " WHERE url = #{url}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "url",column = "url"),
            @Result(property = "time",column = "time"),
            @Result(property = "category",column = "category"),
            @Result(property = "tag",column = "tag"),
            @Result(property = "caseId",column = "case_id")
    })
    Thumb getThumbByUrl(@Param("url")String url);

    //2.根据case_id查询对应的所有thumb
    @Select(" SELECT *" +
            " FROM `thumb`" +
            " WHERE case_id = #{case_id}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "url",column = "url"),
            @Result(property = "time",column = "time"),
            @Result(property = "category",column = "category"),
            @Result(property = "tag",column = "tag"),
            @Result(property = "caseId",column = "case_id")
    })
    List<Thumb> getThumbByCaseId(@Param("caseId") int caseId);

    //3.新建一个thumb
    @Insert(" INSERT INTO `thumb`(url, time, category, tag, case_id)" +
            " VALUES (#{url}, #{time}, #{category}, #{tag}, #{caseId})")
    void createThumb(Thumb thumb);

}
