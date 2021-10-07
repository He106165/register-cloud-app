package com.censoft.demo.controller;

import com.censoft.common.redis.util.RedisUtils;
import com.censoft.demo.pojo.User;
import com.censoft.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    /**
    * @Description 查询所有用户
    * @Parm
    * @return
    **/
    @GetMapping("/Users")
    public List selectAll() {
        List<User> users = userService.selectAll();
        return users;
    }

    @Autowired
    private RedisUtils redis;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @GetMapping("/ss")
    @ResponseBody
    public Set ss() {
        redis.set("111","dsa");
        Set<String> keys=redisTemplate.keys("#Alipay_*");
        redisTemplate.delete(keys);
        keys=redisTemplate.keys("#Alipay_*");
        return keys;
    }

    @GetMapping("/getOne/{id}")
    public String getOne( String id) {
        User user = userService.getOne(Integer.valueOf(id));
        return user + "";
    }

    @PostMapping("/add")
    public String add(User user) {
            userService.add(user);

        return "nice";
    }
    @RequestMapping(value = "test",method = {RequestMethod.POST,RequestMethod.GET})
    @PutMapping("/update")
    public String update(User user) {
        userService.update(user);
        return "nice";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable Integer id) {
        userService.delete(id);
        return "nice";
    }

    /**
     *   1. 注释
     *   2. 参数判空
     *   3. 赠、删、改 有返回值，根据返回值判断是否成功
     *   4. id 是随机串
     *   5. 方法起名  见名知意
     *   6. 方法使用类型使用 get 或者post 、RequestMapping
     *   mapper文件
     *   1. 使用提出来的公用的sql
     *   2. 避免使用select *
     *   3. insert update 使用<if test=""></if>   判断空值
     *   4. 删除是逻辑删除
     *
     *   修改前端步骤
     *   1. 路由位置 registermanage-cloud-ant/src/utils/routerUtil.js
     *   2. 复制组件  import Descriptions from '@/components/Descriptions/Descriptions'
     *   2. 复制 a-modal 弹框  ，修改对应的字段信息
     *   3. 复制
     *      // 详情页面
     *       loading: false,
     *       visible: false,
     *   4. 复制 弹框方法 showmodel
     *
     * */


}



