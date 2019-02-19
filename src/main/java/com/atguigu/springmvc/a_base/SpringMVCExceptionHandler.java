package com.atguigu.springmvc.a_base;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;


/**
 * 异常处理方式1
 * 1. @ExceptionHandler 方法的入参中可以加入 Exception 类型的参数, 该参数即对应发生的异常对象
 * 2. @ExceptionHandler 方法的入参中不能传入 Map. 若希望把异常信息传导页面上, 需要使用 ModelAndView 作为返回值
 * 3. @ExceptionHandler 方法标记的异常有优先级的问题. 
 * 5. @ControllerAdvice 标记的类对所有Handler有效
 */
@ControllerAdvice
public class SpringMVCExceptionHandler {
	
	/**
	 * 处理全局数学异常
	 * @param ex 异常信息对象
	 * @return 出异常后转发的页面
	 */
	@ExceptionHandler({ArithmeticException.class})
	public ModelAndView ArithmeticExceptionHandler(Exception ex) {
		ModelAndView modelAndView=new ModelAndView("success");
		modelAndView.addObject("exception", ex);
		System.out.println("出异常了:"+ex);
		return modelAndView;
	}
	
	/**
	 * 处理全局运行时异常
	 * @param ex 异常信息对象
	 * @return 出异常后转发的页面
	 */
	@ExceptionHandler({RuntimeException.class})
	public ModelAndView RuntimeExceptionHandler(Exception ex) {
		ModelAndView modelAndView=new ModelAndView("success");
		modelAndView.addObject("exception", ex);
		System.out.println("[出异常了]:"+ex);
		return modelAndView;
	}

}
