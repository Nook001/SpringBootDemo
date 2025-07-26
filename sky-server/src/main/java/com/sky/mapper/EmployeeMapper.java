package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username 用户名
     * @return 员工实体对象
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /**
     * 插入员工数据
     * @param employee 员工实体对象
     * @return 插入成功的行数
     */
    @Insert("insert into employee (id, name, gender, id_number, status, create_time, update_time, create_user, update_user, username, password, phone) " +
            "values (#{id}, #{name}, #{gender}, #{idNumber}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser}, #{username}, #{password}, #{phone})")
    int add(Employee employee);

    Page pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    int update(Employee employee);


    @Select("select * from employee where id = #{id}")
    Employee getById(Long id);
}
