package com.sky.service.impl;

import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 处理员工登录
     * @param employeeLoginDTO 从前端传入的登录信息
     * @return 登录成功的员工实体对象
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(employeeLoginDTO.getUsername());

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        // 用户名不存在
        if (employee == null) {
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        // 密码错误
        else if (!checkPassword(employeeLoginDTO.getPassword(), employee.getPassword())) {
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }
        // 账号被锁定
        else if (employee.getStatus().equals(StatusConstant.DISABLE)) {
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    // 检查密码是否正确
    private boolean checkPassword(String inputPassword, String storedPassword) {
        // 使用MD5加密进行密码比对
        String hashedInputPassword = DigestUtils.md5DigestAsHex(inputPassword.getBytes());
        return hashedInputPassword.equals(storedPassword);
    }

    /**
     * 处理新增员工
     * @param employeeDTO  从前端传入的员工信息
     * @return 是否添加成功
     */
    public boolean addEmployee(EmployeeDTO employeeDTO) {
        // 将前端传入的DTO转换为实体对象
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);

        // 设置性别
        employee.setGender(employeeDTO.getSex());
        // 默认启用账号
        employee.setStatus(StatusConstant.ENABLE);

        // 默认密码为123456
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));

        // 设置创建时间和更新时间
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(employee.getCreateTime());

        // 设置创建人和更新人id
        employee.setCreateUser(BaseContext.getUserId());
        employee.setUpdateUser(BaseContext.getUserId());

        // 执行插入操作
        if (employeeMapper.add(employee) > 0) {
            return true;
        } else {
            throw new RuntimeException("新增员工失败");
        }
    }

}
