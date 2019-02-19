package com.atguigu.springmvc.a_base;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;




/**
 * �쳣����ʽ2 @ResponseStatusע�ⷽ��������
 * @ResponseStatus ��������������,Ҳ���������η���,���η���ʱ��ӵ�Handler��������������
 * ����ע�����η���,���۷����Ƿ���쳣,������ʾ���Ƶ��쳣��Ϣ��״̬��
 * ��ע����Դ����쳣,���Զ����쳣��Ϣ��״̬��
 * @author luxc
 */
@ResponseStatus(value=HttpStatus.FORBIDDEN,reason="�û���������")
public class UserNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

}
