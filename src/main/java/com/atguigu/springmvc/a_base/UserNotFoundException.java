package com.atguigu.springmvc.a_base;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;




/**
 * 异常处理方式2 @ResponseStatus注解方法或者类
 * @ResponseStatus 可以用来修饰类,也可以来修饰方法,修饰方法时添加到Handler的请求处理方法即可
 * 若此注解修饰方法,无论方法是否出异常,都会显示定制的异常信息和状态码
 * 该注解可以处理异常,可以定制异常信息和状态码
 * @author luxc
 */
@ResponseStatus(value=HttpStatus.FORBIDDEN,reason="用户名不存在")
public class UserNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

}
