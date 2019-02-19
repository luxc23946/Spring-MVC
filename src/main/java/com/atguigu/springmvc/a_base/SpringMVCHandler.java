package com.atguigu.springmvc.a_base;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.atguigu.springmvc.b_curd.Employee;
import com.atguigu.springmvc.b_curd.EmployeeDao;

@Controller
@RequestMapping("/springmvc")
@SessionAttributes(value= {"user"},types= {String.class})
public class SpringMVCHandler {
	
	private static final String SUCCESS="success";
	private static final String USER="user";
	
	@Autowired
	private EmployeeDao employeeDao;
	
	//��Handler�л�ȡ���ʻ���Ϣ,��ֻ����ҳ������ʾ���ʻ���Ϣ,����Բ���ע��ò���
	@Autowired
	private ResourceBundleMessageSource messageSource;
	
	/**��������url
	 * 1. @RequestMapping �������η���, ������������ 2. 
	 * 1). �ඨ�崦: �ṩ����������ӳ����Ϣ������� WEB Ӧ�õĸ�Ŀ¼
	 * 2). ������: �ṩ��һ����ϸ��ӳ����Ϣ�� ������ඨ�崦�� URL�����ඨ�崦δ��ע @RequestMapping���򷽷�����ǵ� URL
	 * ����� WEB Ӧ�õĸ�Ŀ¼
	 */
	@RequestMapping("/helloWorld")
	public String hello() {
		System.out.println("Hello World!!!");
		return SUCCESS;
	}
	
	/**
	 * ����: ʹ�� method ������ָ������ʽ
	 * 1)RequestMethod.POST
	 * 2)RequestMethod.GET
	 * 3)RequestMethod.DELETE
	 * 4)RequestMethod.PUT
	 */
	@RequestMapping(value="/testMethod",method=RequestMethod.POST)
	public String testMethod() {
		System.out.println("testMethod!!!");
		return SUCCESS;
	}
	
	/**
	 * params:ָ������url�����������������������ĳЩ���������ܰ���ĳЩ�����ȣ���:
	 * 1)"param1": ��ʾ������������Ϊ param1 ���������
	 * 2)"!param1": ��ʾ�����ܰ�����Ϊ param1 ���������
	 * 3)"param1!= value1": ��ʾ���������Ϊ param1 ���������������ֵ����Ϊ value1
	 * 4)
	 * 
	 */
	@RequestMapping(value="testParamsAndHeaders",params= {"name","age!=10"},
			headers= {"Host=localhost"},method=RequestMethod.GET)
	public String testParamsAndHeaders() {
		System.out.println("testParamsAndHeaders");
		return SUCCESS;
	}
	
	/**
	 * @RequestMapping ��֧�� Ant ���� URL
	 * Ant �����Դ��ַ֧�� 3 ��ƥ�����
     * 1) ?ƥ���ļ����е�һ���ַ� 
     * 2) *ƥ���ļ����е������ַ� �C
     * 3) ** ƥ����·��
	 * @return
	 */
	@RequestMapping("/testAntPath/*/abc")
	public String testAntPath() {
		System.out.println("testAntPath");
		return SUCCESS;
	}
	
	/**
	 * @PathVariable ������ӳ�� URL �е�ռλ����Ŀ�귽���Ĳ�����.
	 *  �����ṩ������404����
	 */
	@RequestMapping("/testPathVariable/{id}")
	public String TestPathVariable(@PathVariable("id") Integer id) {
		System.out.println("testPathVariable:id="+id);
		return SUCCESS;
	}
	
	
	/**
	 * Rest ���� URL. �� CRUD Ϊ��: 
	 * ����: /order     POST 
	 * �޸�: /order/1   PUT    update?id=1 
	 * ��ȡ: /order/1   GET    get?id=1 
	 * ɾ��: /order/1   DELETE delete?id=1
	 * 
	 * ��η��� PUT ����� DELETE ������ ? 
	 * 1). ��Ҫ��XML������ HiddenHttpMethodFilter 
	 * 2). ��Ҫ���� POST ����
	 * 3). ��Ҫ�ڷ��� POST ����ʱЯ��һ�� name="_method" ��������, ֵΪ DELETE �� PUT
	 * �� SpringMVC ��Ŀ�귽������εõ� id ��? ʹ�� @PathVariable ע��
	 * 
	 * ���ڴ��� PUT ����
	 */
	@RequestMapping(value="testRest/{id}",method=RequestMethod.PUT)
	public String testRestPut(@PathVariable("id") Integer id) {
		System.out.println("testRest:PUT-"+id);
		return SUCCESS;
	}
	
	
	/**
	 * ���ڴ��� GET ����
	 */
	@RequestMapping(value="testRest/{id}",method=RequestMethod.GET)
	public String testRestGet(@PathVariable("id") Integer id) {
		System.out.println("testRest:GET-"+id);
		return SUCCESS;
	}
	
	/**
	 * ���ڴ��� POST ����
	 */
	@RequestMapping(value="testRest/{id}",method=RequestMethod.POST)
	public String testRestPost(@PathVariable("id") Integer id) {
		System.out.println("testRest:POST-"+id);
		return SUCCESS;
	}
	
	/**
	 * ���ڴ��� DELETE ����
	 */
	@RequestMapping(value="testRest/{id}",method=RequestMethod.DELETE)
	public String testRestDelete(@PathVariable("id") Integer id) {
		System.out.println("testRest:DELETE-"+id);
		return SUCCESS;
	}
	
	
	/**
	 * @RequestParam ��ӳ���������. 
	 *  value: ����������Ĳ����� 
	 *  required: �ò����Ƿ����. Ĭ��Ϊ true
	 *  defaultValue: ���������Ĭ��ֵ
	 */
	@RequestMapping("testRequestParam")
	public String testRequestParam(
			@RequestParam(value="name",required=true) String name,
			@RequestParam(value="age",required=false,defaultValue="10") Integer age) {
		System.out.println("testRequestParam:name="+name+" age="+age);
		return SUCCESS;
	}
	
	
	/**
	 * @RequestHeader
	 * ӳ������ͷ��Ϣ �÷�ͬ  @RequestParam
	 */
	@RequestMapping("testRequestHeader")
	public String testRequestHeader(
			@RequestHeader("Accept-Language") String lan,
			@RequestHeader("Host") String host) {
		System.out.println("testRequestHeader:lan="+lan+" host="+host);
		return SUCCESS;
	}

	/**
	 * @CookieValue: ӳ��һ�� Cookie ֵ. �÷�ͬ @RequestParam
	 */
	@RequestMapping("testCookieValue")
    public String testCookieValue(
    		@CookieValue("JSESSIONID") String jessionid ) {
    	System.out.println("testCookieValue:jessionid="+jessionid);
    	return SUCCESS;
    }

	
	/**
	 * Spring MVC �ᰴ����������� POJO �����������Զ�ƥ�䣬 �Զ�Ϊ�ö����������ֵ��֧�ּ������ԡ�
	 * �磺dept.deptId��dept.address.tel ��
	 */
	@RequestMapping("testPojo")
	public String testPojo(User user) {
		System.out.println("testPojo:user="+user);
		return SUCCESS;
	}
	
	
	/**
	 * ����ʹ�� Serlvet ԭ���� API ��ΪĿ�귽���Ĳ��� ����֧����������
	 * 
	 * 1).HttpServletRequest 
	 * 2).HttpServletResponse 
	 * 3).HttpSession
	 * 4).java.security.Principal 
	 * 5).Locale 
	 * 6).InputStream 
	 * 7).OutputStream 
	 * 8).Reader 
	 * 9).Writer
	 * @throws IOException 
	 */
	@RequestMapping("testServletApi")
	public String testServletApi(HttpServletRequest request) {
		String servletPath=request.getServletPath();
		System.out.println("testServletApi:servletPath="+servletPath);
		return SUCCESS;
	}
	
	
	/**
	 * Ŀ�귽���ķ���ֵ������ ModelAndView ���͡� 
	 * ���п��԰�����ͼ��ģ����Ϣ
	 * SpringMVC ��� ModelAndView �� model �����ݷ��뵽 request �������. 
	 */
	@RequestMapping("testModelAndView")
	public ModelAndView testModelAndView() {
		ModelAndView modelAndView=new ModelAndView(SUCCESS);
		modelAndView.addObject("time", new Date());
		return modelAndView;
	}
	
	
	/**
	 * Ŀ�귽��������� Map ����(ʵ����Ҳ������ Model ���ͻ� ModelMap ����)�Ĳ���. 
	 * map�е����ݿ�����request������л�ȡ��
	 */
	@RequestMapping("/testMap")
	public String testMap(Map<String, Object> map){
		System.out.println("testMap"); 
		map.put("names", Arrays.asList("Tom", "Jerry", "Mike"));
		return SUCCESS;
	}
	
	
	/**
	 * @SessionAttributes ���˿���ͨ��������ָ����Ҫ�ŵ��Ự�е�������(ʵ����ʹ�õ��� value ����ֵ),
	 * ������ͨ��ģ�����ԵĶ�������ָ����Щģ��������Ҫ�ŵ��Ự��(ʵ����ʹ�õ��� types ����ֵ)
	 * 
	 * ע��: ��ע��ֻ�ܷ����������. ���������ηŷ���. 
	 */
	@RequestMapping("/testSessionAttributes")
	public String testSessionAttributes(Map<String, Object> map){
		User user = new User("Tom", "123456", "tom@atguigu.com", 15);
		map.put("user", user);
		map.put("school", "atguigu");
		return SUCCESS;
	}
	
	
	/**
	 * �Զ�����ͼ ����ͨ����ͼ��ֱ�Ӹ���ͻ�����Ӧ,������HTML��ʽ,���ĵ���ʽ,�򱨱��ʽ��
	 * Ĭ�ϵ���ͼ��������InternalResourceViewResolver,����ͼ�����������ȼ����
	 * 1).��д��ͼ��ʵ�� org.springframework.web.servlet.View�ӿ�
	 * 2).������ͼ����IOC����(���@Componentע��)
	 * 3).��IOC��������� BeanNameViewResolver��ͼ������,ʹ����ͼ��������������ͼ ,��ָ������ͼ��������˳��
	 * 4).SpringMVC��ͨ������ֱֵ�Ӷ�λ��ĳ��ͼ,����ֵΪ������һ����ĸСд.
	 */
	@RequestMapping("/nameView")
	public String testNameView() {
		System.out.println("testNameView");
		return "nameView";
	}
	
	
	/**
	 * ת�����ض���
	 * ������ص��ַ����д� forward: �� redirect: ǰ׺ʱ��SpringMVC ������ǽ������⴦��
	 * redirect:success.jsp�������һ���� success.jsp ���ض���Ĳ��� 
     * forward:success.jsp�������һ���� success.jsp ��ת������ 
	 */
	@RequestMapping("/testRedirect")
	public String testRedirect(){
		System.out.println("testRedirect");
		return "redirect:/index.jsp";
	}
	
	
	/**
	 * ��������:
	 * 1. ִ�� @ModelAttribute ע�����εķ���: �����ݿ���ȡ������, �Ѷ�����뵽�� Map ��. ��Ϊ: user
	 * 2. SpringMVC �� Map ��ȡ�� User ����, ���ѱ���������������� User ����Ķ�Ӧ����.
	 * 3. SpringMVC ������������Ŀ�귽���Ĳ���. 
	 * 
	 * ע��: �� @ModelAttribute ���εķ�����, ���뵽 Map ʱ�ļ���Ҫ��Ŀ�귽��������͵ĵ�һ����ĸСд���ַ���һ��!
	 * 
	 * SpringMVC ȷ��Ŀ�귽�� POJO ������εĹ���
	 * 1. ȷ��һ�� key:
	 * 1). ��Ŀ�귽���� POJO ���͵Ĳ���û��ʹ�� @ModelAttribute ��Ϊ����, �� key Ϊ POJO ������һ����ĸ��Сд
	 * 2). ��ʹ����  @ModelAttribute ������, �� key Ϊ @ModelAttribute ע��� value ����ֵ. 
	 * 2. �� implicitModel �в��� key ��Ӧ�Ķ���, ������, ����Ϊ��δ���
	 * 1). ���� @ModelAttribute ��ǵķ������� Map �б����, �� key �� 1 ȷ���� key һ��, ����ȡ��. 
	 * 3. �� implicitModel �в����� key ��Ӧ�Ķ���, ���鵱ǰ�� Handler �Ƿ�ʹ�� @SessionAttributes ע������, 
	 * ��ʹ���˸�ע��, �� @SessionAttributes ע��� value ����ֵ�а����� key, ���� HttpSession ������ȡ key ��
	 * ��Ӧ�� value ֵ, ��������ֱ�Ӵ��뵽Ŀ�귽���������. �����������׳��쳣. 
	 * 4. �� Handler û�б�ʶ @SessionAttributes ע��� @SessionAttributes ע��� value ֵ�в����� key, ��
	 * ��ͨ������������ POJO ���͵Ĳ���, ����ΪĿ�귽���Ĳ���
	 * 5. SpringMVC ��� key �� POJO ���͵Ķ��󱣴浽 implicitModel ��, �����ᱣ�浽 request ��. 
	 * 
	 * Դ�������������
	 * 1. ���� @ModelAttribute ע�����εķ���. ʵ���ϰ� @ModelAttribute ������ Map �е����ݷ����� implicitModel ��.
	 * 2. ��������������Ŀ�����, ʵ���ϸ�Ŀ����������� WebDataBinder ����� target ����
	 * 1). ���� WebDataBinder ����:
	 * ��. ȷ�� objectName ����: ������� attrName ����ֵΪ "", �� objectName Ϊ������һ����ĸСд. 
	 * *ע��: attrName. ��Ŀ�귽���� POJO ����ʹ���� @ModelAttribute ������, �� attrName ֵ��Ϊ @ModelAttribute 
	 * �� value ����ֵ 
	 * 
	 * ��. ȷ�� target ����:
	 * 	> �� implicitModel �в��� attrName ��Ӧ������ֵ. ������, ok
	 * 	> *��������: ����֤��ǰ Handler �Ƿ�ʹ���� @SessionAttributes ��������, ��ʹ����, ���Դ� Session ��
	 * ��ȡ attrName ����Ӧ������ֵ. �� session ��û�ж�Ӧ������ֵ, ���׳����쳣. 
	 * 	> �� Handler û��ʹ�� @SessionAttributes ��������, �� @SessionAttributes ��û��ʹ�� value ֵָ���� key
	 * �� attrName ��ƥ��, ��ͨ�����䴴���� POJO ����
	 * 
	 * 2). SpringMVC �ѱ���������������� WebDataBinder �� target ��Ӧ������. 
	 * 3). *SpringMVC ��� WebDataBinder �� attrName �� target ���� implicitModel. 
	 * �������� request �������. 
	 * 4). �� WebDataBinder �� target ��Ϊ�������ݸ�Ŀ�귽�������. 
	 */
	@RequestMapping("/testModelAttribute")
	public String testModelAttribute(@ModelAttribute("user") User user){
		System.out.println("�޸�: " + user);
		return USER;
	}
	
	
	/**
	 * �Զ�������ת����
	 * 1).��дת������,ʵ��Converter�ӿ�,������ת��������IOC����(���@Compontע��)
	 * 2).��XML������ConversionServiceFactoryBean:��1ת���������������ת��������
	 * 3).��mvc:annotation-driven ��ָ�� conversion-service����
	 */
	@RequestMapping(value="testConverter",method=RequestMethod.POST)
	public String testConverter(@RequestParam("user") User user,
			Map<String,Object> requestMap) {
		System.out.println("testConverter:"+user);
		requestMap.put("user", user);
		return SUCCESS;
	}
	
	
	/**
	 *  
	 * ����У��. 
	 * 1.���У�� ? ע�� .
	 *  1). ʹ�� JSR 303 ��֤��׼
	 *  2). ���� hibernate validator ��֤��ܵ� jar ��
     *  3). �� SpringMVC �����ļ������ <mvc:annotation-driven />
	 *  4). ��Ҫ�� bean ����������Ӷ�Ӧ��ע��
	 *  5). ��Ŀ�귽�� bean ���͵�ǰ����� @Valid ע��
	 *  ע��: ��У��� Bean �������󶨽�������������ʱ�ɶԳ��ֵģ�����֮�䲻�����������������
	 * 2.��֤����ת����һ��ҳ�� ? ͨ������ֵת�������ض���
	 * 3. ������Ϣ , �����ʾ?, ��ΰѴ�����Ϣ���й��ʻ�?
	 *  1).��ʾͨ�� <form:errors path=""/>��ǩ,���˱�ǩ������<form:form modelAttribute="">֮��
	 *       ��path="*"����ʾ���д�����Ϣ.��path="key"����ʾĳ��������Ϣ
	 *  2).���ʻ� ��=��֤ע����+�������+������(NotEmpty.user.username),ֵ=��Ҫ��ʾ�Ĵ�����Ϣ
	 *       Ȼ��ͨ��<form:errors path=""/>��ʾ
	 * ��֤ʧ�ܵ���Ϣ�Զ�����BindingResult,��Errors��
	 */
	@RequestMapping("/testValidate")
	public String testValidate(@Valid @ModelAttribute("user") User user,BindingResult result,
			Map<String, Object>requestMap) {
		System.out.println("testValidate:user="+user);
		if(result.hasErrors()) {
			System.out.println("������:");
			for(FieldError error:result.getFieldErrors()) {
				System.out.println(error.getField()+":"+error.getDefaultMessage());
			}
			//requestMap.put("inputErr", result.getFieldErrors());
			//����֤������ת���ҳ��
			//return "forward:/index.jsp";
		}
		return SUCCESS;
	}
	
	
	/**
	 * Ajax����JSON
	 * 1).���jackson(jackson-core.jar,jackson-annotations.jar,jackson-databind.jar)
	 * 2).����������ֵ��ΪAjax��Ӧ
	 * 3).�ڴ���Ajax����ķ��������@ResponseBodyע��
	 */
	@ResponseBody
	@RequestMapping("/testJson")
	public Collection<Employee> testJson() {
		return employeeDao.getAll();
	}
	
	
	/**
	 * HttpMessageConverter<T>�� Spring3.0 ����ӵ�һ���� �ڣ�����������Ϣת��
	 * Ϊһ������(����Ϊ T),������(����Ϊ T)���Ϊ��Ӧ��Ϣ,���кܶ�ʵ����,
	 * ��StringHttpMessageConverter��ʵ������Խ��������װ���ַ�������.
	 * ��byte���ͷ���ֵ�������ļ�����
	 * Spring �ṩ������;��:
     * 1).ʹ�� @RequestBody / @ResponseBody  �Դ��������б�ע
     * 2).ʹ�� HttpEntity<T> / ResponseEntity<T> ��Ϊ����������λ򷵻�ֵ
     * 3). @RequestBody ��  @ResponseBody ����Ҫ�ɶԳ���
	 */
	@ResponseBody
	@RequestMapping("testHttpMessageConverter")
	public String testHttpMessageConverter(@RequestBody String str) {
		System.out.println(str);
		return "Hello!!!";
	}
	
	
	
	/**
	 * �ļ�����
	 * @return ResponseEntity
	 */
	@RequestMapping("testDownload")
	public ResponseEntity<byte[]> testDownload(HttpServletRequest request) throws IOException{
		byte[] body=null;
		InputStream in=request.getServletContext().getResourceAsStream("/files/abc.txt");
		body=new byte[in.available()];
		in.read(body);
		HttpHeaders headers=new HttpHeaders();
		headers.add("Content-Disposition", "attachment;filename=abc.txt");
		headers.add("Content-Type", "text/plain");
		HttpStatus statusCode=HttpStatus.OK;
		ResponseEntity<byte[]> response=new ResponseEntity<byte[]>(body, headers, statusCode);
		return response;
	}
	
	
	/**
	 * ��Handler�ڴ�ӡ���ʻ���Ϣ
	 * ע��:i18nֻ�о���Handler�Ż���Ч
	 */
	@RequestMapping("testI18n")
	public String testI18n(Locale locale) {
		String val=messageSource.getMessage("i18n.hello",null, locale);
		System.out.println(val);
		return SUCCESS;
	}
	
	/**
	 * �ļ��ϴ�
	 * MultipartFile ����������ȡ�ļ���Դ��Ϣ
	 */
	@RequestMapping("testFileUpload")
	public String testFileUpload(@RequestParam("desc") String desc,
			@RequestParam("file") MultipartFile file) throws IOException {
		System.out.println("desc:"+desc);
		System.out.println("originalFilename:"+file.getOriginalFilename());
		System.out.println("contentType:"+file.getContentType());
		System.out.println("size:"+file.getSize());
		System.out.println("inputStream:"+file.getInputStream());
		return SUCCESS;
	}
	
	/**
	 * �쳣����ʽһ: ���� @ExceptionHandler ע��
	 */
	@RequestMapping("/testExceptionHandlerExceptionResolver")
	public String testExceptionHandlerExceptionResolver() {
		System.out.println(10/0);
		return SUCCESS;
	}
	
	/**
	 * �쳣����ʽ��: ����  @ResponseStatus ע��
	 */
	@RequestMapping("testResponseStatusExceptionResolver")
	public String testResponseStatusExceptionResolver(@RequestParam("i") Integer i) 
			throws UserNotFoundException {
		if(i/2==5) {
			throw new UserNotFoundException();
		}
		return SUCCESS;
	}
	
	/**
	 * �쳣����ʽ��: ����XML
	 */
	@RequestMapping("testSimpleMappingExceptionResolver")
	public String testSimpleMappingExceptionResolver(@RequestParam("i") Integer i) throws UserLockedException {
		if(i/2==5) {
			throw new UserLockedException();
		}
		return SUCCESS;
	}
	
	
	/**
	 * 1. @ExceptionHandler ����������п��Լ��� Exception ���͵Ĳ���, �ò�������Ӧ�������쳣����
	 * 2. @ExceptionHandler ����������в��ܴ��� Map. ��ϣ�����쳣��Ϣ����ҳ����, ��Ҫʹ�� ModelAndView ��Ϊ����ֵ
	 * 3. @ExceptionHandler ������ǵ��쳣�����ȼ�������. 
	 * 4. @ExceptionHandler ��ǵķ���ֻ�Ե�ǰHandler��Ч
	 * 5. @ControllerAdvice ��ǵ��������Handler��Ч
	 */
//	@ExceptionHandler({ArithmeticException.class})
//	public ModelAndView ArithmeticExceptionHandler(Exception ex) {
//		ModelAndView modelAndView=new ModelAndView("success");
//		modelAndView.addObject("exception", ex);
//		System.out.println("���쳣��:"+ex);
//		return modelAndView;
//	}
	
//	@ExceptionHandler({RuntimeException.class})
//	public ModelAndView RuntimeExceptionHandler(Exception ex) {
//		ModelAndView modelAndView=new ModelAndView("success");
//		modelAndView.addObject("exception", ex);
//		System.out.println("[���쳣��]:"+ex);
//		return modelAndView;
//	}
	
	
	/**
	 * �� @InitBinder ��ʶ�ķ��� ,���Զ� WebDataBinder ������г�ʼ��
	 * 1).@InitBinder���������з���ֵ������������Ϊvoid
	 * 2).@InitBinder�����Ĳ���ͨ������ WebDataBinder
	 */
//	@InitBinder
//	public void testInitBinder(WebDataBinder binder) {
//		binder.setDisallowedFields("username");
//	}
	
	
	/**
	 * 1. �� @ModelAttribute ��ǵķ���, ����ÿ��Ŀ�귽��ִ��֮ǰ�� SpringMVC ����! 
	 * 2. �� @ModelAttribute ע��Ҳ����������Ŀ�귽�� POJO ���͵����, �� value ����ֵ�����µ�����:
	 * 1). SpringMVC ��ʹ�� value ����ֵ�� implicitModel �в��Ҷ�Ӧ�Ķ���, ���������ֱ�Ӵ��뵽Ŀ�귽���������.
	 * 2). SpringMVC ���� value Ϊ key, POJO ���͵Ķ���Ϊ value, ���뵽 request ��. 
	 */
	@ModelAttribute
	public void getUser(@RequestParam(value="id",required=false) Integer id, 
			Map<String, Object> map){
		if(id != null){
			//ģ������ݿ��л�ȡ����
			User user = new User(id, "bbb", "bbb", "bb@bbb.com", 12);
			map.put("user", user);
		}
	}
	
	
	
	
}
