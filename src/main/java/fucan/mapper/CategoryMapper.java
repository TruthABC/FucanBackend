package fucan.mapper;

import fucan.entity.mapping.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CategoryMapper {

    //1.得到所有分类名
    @Select(" SELECT *" +
            " FROM `category`" +
            " LIMIT 1000")
    @Results({
            @Result(property = "name", column = "name")
    })
    List<Category> getCategory();

}
