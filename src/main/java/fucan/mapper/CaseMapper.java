package fucan.mapper;

import fucan.entity.mapping.Case;
import fucan.entity.mapping.Thumb;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CaseMapper {

    //1.获取所有病例
    @Select(" SELECT *" +
            " FROM `case`" +
            " LIMIT 1000")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "time", column = "time"),
            @Result(property = "thumbUrl", column = "thumbUrl"),
            @Result(property = "state", column = "state"),
            @Result(property = "progress", column = "progress"),
            @Result(property = "totalCount", column = "totalCount"),
            @Result(property = "positiveCount", column = "positiveCount"),
            @Result(property = "negativeCount", column = "negativeCount"),
            @Result(property = "directory", column = "directory")
    })
    List<Case> getCase();

    //子查询：根据case_id查询对应的所有thumb
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

    //2.根据id获取病例
    @Select(" SELECT *" +
            " FROM `case`" +
            " WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "time", column = "time"),
            @Result(property = "thumbUrl", column = "thumbUrl"),
            @Result(property = "state", column = "state"),
            @Result(property = "progress", column = "progress"),
            @Result(property = "totalCount", column = "totalCount"),
            @Result(property = "positiveCount", column = "positiveCount"),
            @Result(property = "negativeCount", column = "negativeCount"),
            @Result(property = "directory", column = "directory"),
            @Result(property = "thumbs",column = "id",many = @Many(select="getThumbByCaseId"))
            //这里我们使用了@Many注解的select属性来指向一个完全限定名方法，
            //该方法将返回一个List<>对象。使用column="id"，case数据表中的"id"列值将会作为输入参数传递给指定的方法。
    })
    Case getCaseById(@Param("id") int id);

    //3.更新state
    @Update(" UPDATE `case`" +
            " SET name = #{name}," +
            "  time = #{time}," +
            "  thumbUrl = #{thumbUrl}," +
            "  state = #{state}," +
            "  progress = #{progress}," +
            "  totalCount = #{totalCount}," +
            "  positiveCount = #{positiveCount}," +
            "  negativeCount = #{negativeCount}," +
            "  directory = #{directory}" +
            " WHERE id = #{id}")
    void updateCase(Case cas);

}
