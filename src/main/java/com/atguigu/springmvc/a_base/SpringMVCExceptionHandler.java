package com.atguigu.springmvc.a_base;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;


/**
 * �쳣����ʽ1
 * 1. @ExceptionHandler ����������п��Լ��� Exception ���͵Ĳ���, �ò�������Ӧ�������쳣����
 * 2. @ExceptionHandler ����������в��ܴ��� Map. ��ϣ�����쳣��Ϣ����ҳ����, ��Ҫʹ�� ModelAndView ��Ϊ����ֵ
 * 3. @ExceptionHandler ������ǵ��쳣�����ȼ�������. 
 * 5. @ControllerAdvice ��ǵ��������Handler��Ч
 */
@ControllerAdvice
public class SpringMVCExceptionHandler {
	
	/**
	 * ����ȫ����ѧ�쳣
	 * @param ex �쳣��Ϣ����
	 * @return ���쳣��ת����ҳ��
	 */
	@ExceptionHandler({ArithmeticException.class})
	public ModelAndView ArithmeticExceptionHandler(Exception ex) {
		ModelAndView modelAndView=new ModelAndView("success");
		modelAndView.addObject("exception", ex);
		System.out.println("���쳣��:"+ex);
		return modelAndView;
	}
	
	/**
	 * ����ȫ������ʱ�쳣
	 * @param ex �쳣��Ϣ����
	 * @return ���쳣��ת����ҳ��
	 */
	@ExceptionHandler({RuntimeException.class})
	public ModelAndView RuntimeExceptionHandler(Exception ex) {
		ModelAndView modelAndView=new ModelAndView("success");
		modelAndView.addObject("exception", ex);
		System.out.println("[���쳣��]:"+ex);
		return modelAndView;
	}

}
