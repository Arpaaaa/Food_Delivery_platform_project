package com.arpat.fooddelivery.controller;


import com.arpat.fooddelivery.common.R;
import com.arpat.fooddelivery.entity.Employee;
import com.arpat.fooddelivery.service.EmployeeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    /**
     * 员工登录
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {

        //将页面提交的密码进行md5进行加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //根据用户名查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        //判断用户名是否在数据库存在
        if(emp == null){
            return R.error("登录失败");
        }

        //比对密码是否正确
        if(!emp.getPassword().equals(password)){
            return R.error("密码错误");
        }

        //查看员工的状态，若员工状态为禁用，则返回失败
        if(emp.getStatus()==0){
            return R.error("账号已被禁用");
        }

        //登录成功，将用户id放入session中并返回成功登录结果
        request.getSession().setAttribute("employee",emp.getId());

        return R.success(emp);
    }


    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        //清理session中保存的员工记录
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }
}
