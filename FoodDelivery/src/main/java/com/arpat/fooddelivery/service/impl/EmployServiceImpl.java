package com.arpat.fooddelivery.service.impl;

import com.arpat.fooddelivery.entity.Employee;
import com.arpat.fooddelivery.mapper.EmployeeMapper;
import com.arpat.fooddelivery.service.EmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class EmployServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
