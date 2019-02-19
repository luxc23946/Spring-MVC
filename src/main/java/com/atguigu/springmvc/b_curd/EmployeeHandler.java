package com.atguigu.springmvc.b_curd;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RequestMapping("/curd")
@Controller
public class EmployeeHandler {

	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private DepartmentDao departmentDao;
	
	
	@RequestMapping("emps")
	public String list(Map<String, Object> requestMap) {
		
		requestMap.put("emps", employeeDao.getAll());
		return "list";
	}
	
	@RequestMapping(value="emp/{id}",method=RequestMethod.GET)
	public String input(@PathVariable("id") Integer id,
			Map<String,Object> requestMap) {
		requestMap.put("emp", employeeDao.get(id));
		requestMap.put("depts", departmentDao.getDepartments());
		return "input";
	}
	
	
	@RequestMapping(value="emp",method=RequestMethod.GET)
	public String input(Map<String, Object> requestMap) {
		requestMap.put("emp", new Employee());
		requestMap.put("depts", departmentDao.getDepartments());
		return "input";
	}
	
	@RequestMapping(value="/emp/{id}",method=RequestMethod.DELETE)
	public String delete(@PathVariable("id") Integer id) {
		employeeDao.delete(id);
		return "redirect:/curd/emps";
	}
	
	@RequestMapping(value="/emp",method=RequestMethod.PUT)
	public String update(@ModelAttribute(value="emp") Employee employee) {
		employeeDao.save(employee);
		return "redirect:/curd/emps";
	}
	
	
	@ModelAttribute
	public void getModel(@RequestParam(value="id",required=false) Integer id,
			Map<String, Object>requestMap) {
		if(id!=null) {
			requestMap.put("emp", employeeDao.get(id));
		}
		
	}
	
}
