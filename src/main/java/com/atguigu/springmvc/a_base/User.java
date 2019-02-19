package com.atguigu.springmvc.a_base;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

public class User {
	
	private Integer id;
	@NotEmpty
	private String username;
	@NotEmpty
	private String password;
	@Email
	private String email;
	private Integer age;
	private Address address;
	
	@Past
	@DateTimeFormat(pattern="yyyy/MM/dd")
	private Date birth;
	
	@Max(value=1000)
	@NumberFormat(pattern="#,###,###.#")
	private Float salary;

	
	public User() {}
	
	public User(String username, String password, String email, Integer age) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.age = age;
	}

	public User(Integer id, String username, String password, String email,
			Integer age) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.age = age;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	
	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public Float getSalary() {
		return salary;
	}

	public void setSalary(Float salary) {
		this.salary = salary;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password="
				+ password + ", email=" + email + ", age=" + age + ", address="
				+ address + ", birth=" + birth + ", salary=" + salary + "]";
	}

	
	
}
