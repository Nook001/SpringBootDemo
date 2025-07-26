package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 处理员工登录
     * @param employeeLoginDTO 从前端传入的登录信息
     * @return 登录成功的员工实体对象
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 处理新增员工
     * @param employeeDTO  从前端传入的员工信息
     * @return 是否添加成功
     */
    boolean addEmployee(EmployeeDTO employeeDTO);

    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    boolean changeStatus(Employee employee);

    Employee getById(Long id);
}
