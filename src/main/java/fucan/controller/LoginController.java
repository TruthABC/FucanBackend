package fucan.controller;

import fucan.entity.response.CommonResponse;
import fucan.entity.response.SessionResponse;
import fucan.service.UserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/fucan")
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    //1.登录
    @RequestMapping("/login")
    @CrossOrigin
    public String login(@RequestParam(value="username") String id, @RequestParam(value="password") String password){

        JSONObject jsonRet;

        //登陆失败
        if (!userService.login(id, password)) {
            jsonRet = JSONObject.fromObject(new CommonResponse(1,"用户与密码认证未通过"));
            jsonRet.put("data",new SessionResponse("-1"));
            return jsonRet.toString();
        }

        //登陆成功
        String session = userService.createSession(id);

        jsonRet = JSONObject.fromObject(new CommonResponse(0,""));
        jsonRet.put("data", new SessionResponse(session));
        return jsonRet.toString();
    }

}
