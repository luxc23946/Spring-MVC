package com.atguigu.springmvc.a_base;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserConverter implements Converter<String, User> {


	@Override
	public User convert(String str) {
		System.out.println("converter");
		User user=null;
		if(str!=null){
			//id-username-password-email-age
			//1001---Tom---123456---123@123.com---20
			String [] vals=str.split("---");
			if(vals!=null && vals.length==5) {
				Integer id=Integer.parseInt(vals[0]);
				String username=vals[1];
				String password=vals[2];
				String email=vals[3];
				Integer age=Integer.parseInt(vals[4]);
				user=new User(id, username, password, email, age);
			}
		}
		
		return user;
	}

}
