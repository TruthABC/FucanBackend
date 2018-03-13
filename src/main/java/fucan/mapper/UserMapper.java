package fucan.mapper;

import fucan.entity.mapping.Session;
import fucan.entity.mapping.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    //1.根据id与password得到用户
    @Select(" SELECT *" +
            " FROM `user`" +
            " WHERE id = #{id}" +
            " AND password = #{password}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "password",column = "password")
    })
    List<User> getUserByIdPassword(@Param("id") String id, @Param("password") String password);

    //2.根据user_id与time，为登录用户建立session，并自动填充session的自增id
    @Insert(" INSERT INTO `session`(user_id, time)" +
            " VALUES (#{userId}, #{time})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void createSessionById(Session session);

}
