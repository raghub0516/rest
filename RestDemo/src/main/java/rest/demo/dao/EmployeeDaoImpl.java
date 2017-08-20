package rest.demo.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import rest.demo.bean.Employee;

@Repository("employeeDao")
public class EmployeeDaoImpl implements EmployeeDao {

 private JdbcTemplate jdbcTemplate;

 @Autowired
 public void setDataSource(DataSource dataSource) {
	 System.out.println("Heloo jdbcTemplate");
  this.jdbcTemplate = new JdbcTemplate(dataSource);
 }

 public List<Employee> getEmployees() {
  List<Employee> employees = null ;
  
  try {
   employees = jdbcTemplate.query("SELECT * FROM restdemo",new BeanPropertyRowMapper<Employee>(Employee.class));   
  } catch (DataAccessException e) {
   e.printStackTrace();
  }
  return employees;
 }

 public Employee getEmployee(Long employeeId) {
  Employee employee = null;
  try {
   employee = jdbcTemplate.queryForObject("SELECT * FROM restdemo WHERE employeeId = ?",
     new Object[] { employeeId }, new BeanPropertyRowMapper<Employee>(Employee.class));
  } catch (DataAccessException e) {
   e.printStackTrace();
  }
  return employee;

 }

 public int deleteEmployee(Long employeeId) {
  int count = jdbcTemplate.update("DELETE from restdemo WHERE employeeId = ?", new Object[] { employeeId });
  return count;
 }

 public int updateEmployee(Employee employee) {
  int count = jdbcTemplate.update(
    "UPDATE restdemo set firstName = ? , lastName = ? , age = ? where employeeId = ?", new Object[] {
      employee.getFirstName(), employee.getLastName(), employee.getAge(), employee.getEmployeeId() });
  return count;
 }

 public int createEmployee(Employee employee) {
  int count = jdbcTemplate.update(
    "INSERT INTO restdemo(employeeId,firstName, lastName, age)VALUES(?,?,?,?)", new Object[] {
      employee.getEmployeeId(), employee.getFirstName(), employee.getLastName(), employee.getAge() });
  return count;
 }

}