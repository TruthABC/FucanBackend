package fucan.service;

import fucan.entity.mapping.Session;
import fucan.entity.mapping.User;
import fucan.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    //1.根据id与password登陆
    public boolean login(String id, String password) {
        List<User> user = userMapper.getUserByIdPassword(id, password);
        return user.size() > 0;
    }

    //2.为用户新建一个Session并返回Session的id
    public String createSession(String userId) {
        Session session = new Session(-1, userId, new Timestamp(new Date().getTime()));
        userMapper.createSessionById(session);
        return "" + session.getId();
    }

}
