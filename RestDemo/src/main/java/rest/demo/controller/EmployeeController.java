package rest.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rest.demo.bean.Employee;
import rest.demo.service.EmployeeService;

@RestController
public class EmployeeController {

 @Autowired
 private EmployeeService employeeService;

 @RequestMapping(value = "/employee", method = RequestMethod.GET, produces = "application/json")
 public ResponseEntity<List<Employee>> employees() {
	 System.out.println("employeeClass call");
  List<Employee> employees = employeeService.getEmployees();

  if (employees == null) {
	  System.out.println("Error ");
   return new ResponseEntity<List<Employee>>(HttpStatus.NOT_FOUND);
  }
  System.out.println("Sucess");
  return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
 }

 @RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
 public ResponseEntity<Employee> getEmployee(@PathVariable("id") Long employeeId) {
  Employee employee = employeeService.getEmployee(employeeId);
  if (employee == null) {
   return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
  }
  return new ResponseEntity<Employee>(employee, HttpStatus.OK);
 }

 @RequestMapping(value = "/employee/delete/{id}", method = RequestMethod.DELETE)
 public ResponseEntity<Employee> deleteEmployee(@PathVariable("id") Long employeeId) {
  Employee employee = employeeService.getEmployee(employeeId);
  if (employee == null) {   
   return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
  }
  employeeService.deleteEmployee(employeeId);
  return new ResponseEntity<Employee>(employee, HttpStatus.NO_CONTENT);
 }

 @RequestMapping(value = "/employee", method = RequestMethod.POST,produces = "application/json")
 public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
  if (employee == null) {
   return new ResponseEntity<Employee>(HttpStatus.BAD_REQUEST);
  }
  employeeService.createEmployee(employee);
  return new ResponseEntity<Employee>(employee, HttpStatus.CREATED);
 }

 @RequestMapping(value = "/employee/{id}", method = RequestMethod.PUT)
 public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Long employeeId, @RequestBody Employee employee) {
  Employee isExist = employeeService.getEmployee(employeeId);
  if (isExist == null) {   
   return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
  } 
  employeeService.updateEmployee(employee);
  return new ResponseEntity<Employee>(employee, HttpStatus.OK);
 }

}