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
	
	//在Handler中获取国际化信息,若只是在页面中显示国际化信息,则可以不用注入该参数
	@Autowired
	private ResourceBundleMessageSource messageSource;
	
	/**处理请求url
	 * 1. @RequestMapping 除了修饰方法, 还可来修饰类 2. 
	 * 1). 类定义处: 提供初步的请求映射信息。相对于 WEB 应用的根目录
	 * 2). 方法处: 提供进一步的细分映射信息。 相对于类定义处的 URL。若类定义处未标注 @RequestMapping，则方法处标记的 URL
	 * 相对于 WEB 应用的根目录
	 */
	@RequestMapping("/helloWorld")
	public String hello() {
		System.out.println("Hello World!!!");
		return SUCCESS;
	}
	
	/**
	 * 常用: 使用 method 属性来指定请求方式
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
	 * params:指定请求url必须满足的条件，如必须包含某些参数，不能包含某些参数等，例:
	 * 1)"param1": 表示请求必须包含名为 param1 的请求参数
	 * 2)"!param1": 表示请求不能包含名为 param1 的请求参数
	 * 3)"param1!= value1": 表示请求包含名为 param1 的请求参数，但其值不能为 value1
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
	 * @RequestMapping 还支持 Ant 风格的 URL
	 * Ant 风格资源地址支持 3 种匹配符：
     * 1) ?匹配文件名中的一个字符 
     * 2) *匹配文件名中的任意字符 –
     * 3) ** 匹配多层路径
	 * @return
	 */
	@RequestMapping("/testAntPath/*/abc")
	public String testAntPath() {
		System.out.println("testAntPath");
		return SUCCESS;
	}
	
	/**
	 * @PathVariable 可以来映射 URL 中的占位符到目标方法的参数中.
	 *  若不提供参数则404错误
	 */
	@RequestMapping("/testPathVariable/{id}")
	public String TestPathVariable(@PathVariable("id") Integer id) {
		System.out.println("testPathVariable:id="+id);
		return SUCCESS;
	}
	
	
	/**
	 * Rest 风格的 URL. 以 CRUD 为例: 
	 * 新增: /order     POST 
	 * 修改: /order/1   PUT    update?id=1 
	 * 获取: /order/1   GET    get?id=1 
	 * 删除: /order/1   DELETE delete?id=1
	 * 
	 * 如何发送 PUT 请求和 DELETE 请求呢 ? 
	 * 1). 需要在XML中配置 HiddenHttpMethodFilter 
	 * 2). 需要发送 POST 请求
	 * 3). 需要在发送 POST 请求时携带一个 name="_method" 的隐藏域, 值为 DELETE 或 PUT
	 * 在 SpringMVC 的目标方法中如何得到 id 呢? 使用 @PathVariable 注解
	 * 
	 * 用于处理 PUT 请求
	 */
	@RequestMapping(value="testRest/{id}",method=RequestMethod.PUT)
	public String testRestPut(@PathVariable("id") Integer id) {
		System.out.println("testRest:PUT-"+id);
		return SUCCESS;
	}
	
	
	/**
	 * 用于处理 GET 请求
	 */
	@RequestMapping(value="testRest/{id}",method=RequestMethod.GET)
	public String testRestGet(@PathVariable("id") Integer id) {
		System.out.println("testRest:GET-"+id);
		return SUCCESS;
	}
	
	/**
	 * 用于处理 POST 请求
	 */
	@RequestMapping(value="testRest/{id}",method=RequestMethod.POST)
	public String testRestPost(@PathVariable("id") Integer id) {
		System.out.println("testRest:POST-"+id);
		return SUCCESS;
	}
	
	/**
	 * 用于处理 DELETE 请求
	 */
	@RequestMapping(value="testRest/{id}",method=RequestMethod.DELETE)
	public String testRestDelete(@PathVariable("id") Integer id) {
		System.out.println("testRest:DELETE-"+id);
		return SUCCESS;
	}
	
	
	/**
	 * @RequestParam 来映射请求参数. 
	 *  value: 即请求参数的参数名 
	 *  required: 该参数是否必须. 默认为 true
	 *  defaultValue: 请求参数的默认值
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
	 * 映射请求头信息 用法同  @RequestParam
	 */
	@RequestMapping("testRequestHeader")
	public String testRequestHeader(
			@RequestHeader("Accept-Language") String lan,
			@RequestHeader("Host") String host) {
		System.out.println("testRequestHeader:lan="+lan+" host="+host);
		return SUCCESS;
	}

	/**
	 * @CookieValue: 映射一个 Cookie 值. 用法同 @RequestParam
	 */
	@RequestMapping("testCookieValue")
    public String testCookieValue(
    		@CookieValue("JSESSIONID") String jessionid ) {
    	System.out.println("testCookieValue:jessionid="+jessionid);
    	return SUCCESS;
    }

	
	/**
	 * Spring MVC 会按请求参数名和 POJO 属性名进行自动匹配， 自动为该对象填充属性值。支持级联属性。
	 * 如：dept.deptId、dept.address.tel 等
	 */
	@RequestMapping("testPojo")
	public String testPojo(User user) {
		System.out.println("testPojo:user="+user);
		return SUCCESS;
	}
	
	
	/**
	 * 可以使用 Serlvet 原生的 API 作为目标方法的参数 具体支持以下类型
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
	 * 目标方法的返回值可以是 ModelAndView 类型。 
	 * 其中可以包含视图和模型信息
	 * SpringMVC 会把 ModelAndView 的 model 中数据放入到 request 域对象中. 
	 */
	@RequestMapping("testModelAndView")
	public ModelAndView testModelAndView() {
		ModelAndView modelAndView=new ModelAndView(SUCCESS);
		modelAndView.addObject("time", new Date());
		return modelAndView;
	}
	
	
	/**
	 * 目标方法可以添加 Map 类型(实际上也可以是 Model 类型或 ModelMap 类型)的参数. 
	 * map中的数据可以在request域对象中获取到
	 */
	@RequestMapping("/testMap")
	public String testMap(Map<String, Object> map){
		System.out.println("testMap"); 
		map.put("names", Arrays.asList("Tom", "Jerry", "Mike"));
		return SUCCESS;
	}
	
	
	/**
	 * @SessionAttributes 除了可以通过属性名指定需要放到会话中的属性外(实际上使用的是 value 属性值),
	 * 还可以通过模型属性的对象类型指定哪些模型属性需要放到会话中(实际上使用的是 types 属性值)
	 * 
	 * 注意: 该注解只能放在类的上面. 而不能修饰放方法. 
	 */
	@RequestMapping("/testSessionAttributes")
	public String testSessionAttributes(Map<String, Object> map){
		User user = new User("Tom", "123456", "tom@atguigu.com", 15);
		map.put("user", user);
		map.put("school", "atguigu");
		return SUCCESS;
	}
	
	
	/**
	 * 自定义视图 可以通过试图类直接给与客户端响应,可以是HTML格式,或文档格式,或报表格式等
	 * 默认的视图解析器是InternalResourceViewResolver,该试图解析器的优先级最低
	 * 1).编写视图类实现 org.springframework.web.servlet.View接口
	 * 2).将该视图加入IOC容器(添加@Component注解)
	 * 3).在IOC容器中添加 BeanNameViewResolver视图解析器,使用视图的名字来解析视图 ,并指定该视图解析器的顺序
	 * 4).SpringMVC会通过返回值直接定位到某视图,返回值为类名第一个字母小写.
	 */
	@RequestMapping("/nameView")
	public String testNameView() {
		System.out.println("testNameView");
		return "nameView";
	}
	
	
	/**
	 * 转发和重定向
	 * 如果返回的字符串中带 forward: 或 redirect: 前缀时，SpringMVC 会对他们进行特殊处理
	 * redirect:success.jsp：会完成一个到 success.jsp 的重定向的操作 
     * forward:success.jsp：会完成一个到 success.jsp 的转发操作 
	 */
	@RequestMapping("/testRedirect")
	public String testRedirect(){
		System.out.println("testRedirect");
		return "redirect:/index.jsp";
	}
	
	
	/**
	 * 运行流程:
	 * 1. 执行 @ModelAttribute 注解修饰的方法: 从数据库中取出对象, 把对象放入到了 Map 中. 键为: user
	 * 2. SpringMVC 从 Map 中取出 User 对象, 并把表单的请求参数赋给该 User 对象的对应属性.
	 * 3. SpringMVC 把上述对象传入目标方法的参数. 
	 * 
	 * 注意: 在 @ModelAttribute 修饰的方法中, 放入到 Map 时的键需要和目标方法入参类型的第一个字母小写的字符串一致!
	 * 
	 * SpringMVC 确定目标方法 POJO 类型入参的过程
	 * 1. 确定一个 key:
	 * 1). 若目标方法的 POJO 类型的参数没有使用 @ModelAttribute 作为修饰, 则 key 为 POJO 类名第一个字母的小写
	 * 2). 若使用了  @ModelAttribute 来修饰, 则 key 为 @ModelAttribute 注解的 value 属性值. 
	 * 2. 在 implicitModel 中查找 key 对应的对象, 若存在, 则作为入参传入
	 * 1). 若在 @ModelAttribute 标记的方法中在 Map 中保存过, 且 key 和 1 确定的 key 一致, 则会获取到. 
	 * 3. 若 implicitModel 中不存在 key 对应的对象, 则检查当前的 Handler 是否使用 @SessionAttributes 注解修饰, 
	 * 若使用了该注解, 且 @SessionAttributes 注解的 value 属性值中包含了 key, 则会从 HttpSession 中来获取 key 所
	 * 对应的 value 值, 若存在则直接传入到目标方法的入参中. 若不存在则将抛出异常. 
	 * 4. 若 Handler 没有标识 @SessionAttributes 注解或 @SessionAttributes 注解的 value 值中不包含 key, 则
	 * 会通过反射来创建 POJO 类型的参数, 传入为目标方法的参数
	 * 5. SpringMVC 会把 key 和 POJO 类型的对象保存到 implicitModel 中, 进而会保存到 request 中. 
	 * 
	 * 源代码分析的流程
	 * 1. 调用 @ModelAttribute 注解修饰的方法. 实际上把 @ModelAttribute 方法中 Map 中的数据放在了 implicitModel 中.
	 * 2. 解析请求处理器的目标参数, 实际上该目标参数来自于 WebDataBinder 对象的 target 属性
	 * 1). 创建 WebDataBinder 对象:
	 * ①. 确定 objectName 属性: 若传入的 attrName 属性值为 "", 则 objectName 为类名第一个字母小写. 
	 * *注意: attrName. 若目标方法的 POJO 属性使用了 @ModelAttribute 来修饰, 则 attrName 值即为 @ModelAttribute 
	 * 的 value 属性值 
	 * 
	 * ②. 确定 target 属性:
	 * 	> 在 implicitModel 中查找 attrName 对应的属性值. 若存在, ok
	 * 	> *若不存在: 则验证当前 Handler 是否使用了 @SessionAttributes 进行修饰, 若使用了, 则尝试从 Session 中
	 * 获取 attrName 所对应的属性值. 若 session 中没有对应的属性值, 则抛出了异常. 
	 * 	> 若 Handler 没有使用 @SessionAttributes 进行修饰, 或 @SessionAttributes 中没有使用 value 值指定的 key
	 * 和 attrName 相匹配, 则通过反射创建了 POJO 对象
	 * 
	 * 2). SpringMVC 把表单的请求参数赋给了 WebDataBinder 的 target 对应的属性. 
	 * 3). *SpringMVC 会把 WebDataBinder 的 attrName 和 target 给到 implicitModel. 
	 * 近而传到 request 域对象中. 
	 * 4). 把 WebDataBinder 的 target 作为参数传递给目标方法的入参. 
	 */
	@RequestMapping("/testModelAttribute")
	public String testModelAttribute(@ModelAttribute("user") User user){
		System.out.println("修改: " + user);
		return USER;
	}
	
	
	/**
	 * 自定义类型转换器
	 * 1).编写转换器类,实现Converter接口,并将该转换器加入IOC容器(添加@Compont注解)
	 * 2).在XML中配置ConversionServiceFactoryBean:把1转换器类添加至类型转换器工厂
	 * 3).在mvc:annotation-driven 中指定 conversion-service属性
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
	 * 数据校验. 
	 * 1.如何校验 ? 注解 .
	 *  1). 使用 JSR 303 验证标准
	 *  2). 加入 hibernate validator 验证框架的 jar 包
     *  3). 在 SpringMVC 配置文件中添加 <mvc:annotation-driven />
	 *  4). 需要在 bean 的属性上添加对应的注解
	 *  5). 在目标方法 bean 类型的前面添加 @Valid 注解
	 *  注意: 需校验的 Bean 对象和其绑定结果对象或错误对象时成对出现的，它们之间不允许声明其他的入参
	 * 2.验证出错转向到哪一个页面 ? 通过返回值转发或者重定向
	 * 3. 错误消息 , 如何显示?, 如何把错误消息进行国际化?
	 *  1).显示通过 <form:errors path=""/>标签,但此标签必须在<form:form modelAttribute="">之内
	 *       若path="*"则显示所有错误信息.若path="key"则显示某条错误信息
	 *  2).国际化 键=验证注解名+域对象名+属性名(NotEmpty.user.username),值=需要显示的错误信息
	 *       然后通过<form:errors path=""/>显示
	 * 验证失败的消息自动放入BindingResult,或Errors中
	 */
	@RequestMapping("/testValidate")
	public String testValidate(@Valid @ModelAttribute("user") User user,BindingResult result,
			Map<String, Object>requestMap) {
		System.out.println("testValidate:user="+user);
		if(result.hasErrors()) {
			System.out.println("出错了:");
			for(FieldError error:result.getFieldErrors()) {
				System.out.println(error.getField()+":"+error.getDefaultMessage());
			}
			//requestMap.put("inputErr", result.getFieldErrors());
			//若验证出错，则转向的页面
			//return "forward:/index.jsp";
		}
		return SUCCESS;
	}
	
	
	/**
	 * Ajax返回JSON
	 * 1).添加jackson(jackson-core.jar,jackson-annotations.jar,jackson-databind.jar)
	 * 2).处理方法返回值即为Ajax响应
	 * 3).在处理Ajax请求的方法上添加@ResponseBody注解
	 */
	@ResponseBody
	@RequestMapping("/testJson")
	public Collection<Employee> testJson() {
		return employeeDao.getAll();
	}
	
	
	/**
	 * HttpMessageConverter<T>是 Spring3.0 新添加的一个接 口，负责将请求信息转换
	 * 为一个对象(类型为 T),将对象(类型为 T)输出为响应信息,其有很多实现类,
	 * 如StringHttpMessageConverter该实现类可以将请求域封装成字符串对象.
	 * 如byte类型返回值可以做文件下载
	 * Spring 提供了两种途径:
     * 1).使用 @RequestBody / @ResponseBody  对处理方法进行标注
     * 2).使用 HttpEntity<T> / ResponseEntity<T> 作为处理方法的入参或返回值
     * 3). @RequestBody 和  @ResponseBody 不需要成对出现
	 */
	@ResponseBody
	@RequestMapping("testHttpMessageConverter")
	public String testHttpMessageConverter(@RequestBody String str) {
		System.out.println(str);
		return "Hello!!!";
	}
	
	
	
	/**
	 * 文件下载
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
	 * 在Handler内打印国际化信息
	 * 注意:i18n只有经过Handler才会生效
	 */
	@RequestMapping("testI18n")
	public String testI18n(Locale locale) {
		String val=messageSource.getMessage("i18n.hello",null, locale);
		System.out.println(val);
		return SUCCESS;
	}
	
	/**
	 * 文件上传
	 * MultipartFile 可以用来获取文件的源信息
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
	 * 异常处理方式一: 基于 @ExceptionHandler 注解
	 */
	@RequestMapping("/testExceptionHandlerExceptionResolver")
	public String testExceptionHandlerExceptionResolver() {
		System.out.println(10/0);
		return SUCCESS;
	}
	
	/**
	 * 异常处理方式二: 基于  @ResponseStatus 注解
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
	 * 异常处理方式三: 基于XML
	 */
	@RequestMapping("testSimpleMappingExceptionResolver")
	public String testSimpleMappingExceptionResolver(@RequestParam("i") Integer i) throws UserLockedException {
		if(i/2==5) {
			throw new UserLockedException();
		}
		return SUCCESS;
	}
	
	
	/**
	 * 1. @ExceptionHandler 方法的入参中可以加入 Exception 类型的参数, 该参数即对应发生的异常对象
	 * 2. @ExceptionHandler 方法的入参中不能传入 Map. 若希望把异常信息传导页面上, 需要使用 ModelAndView 作为返回值
	 * 3. @ExceptionHandler 方法标记的异常有优先级的问题. 
	 * 4. @ExceptionHandler 标记的方法只对当前Handler有效
	 * 5. @ControllerAdvice 标记的类对所有Handler有效
	 */
//	@ExceptionHandler({ArithmeticException.class})
//	public ModelAndView ArithmeticExceptionHandler(Exception ex) {
//		ModelAndView modelAndView=new ModelAndView("success");
//		modelAndView.addObject("exception", ex);
//		System.out.println("出异常了:"+ex);
//		return modelAndView;
//	}
	
//	@ExceptionHandler({RuntimeException.class})
//	public ModelAndView RuntimeExceptionHandler(Exception ex) {
//		ModelAndView modelAndView=new ModelAndView("success");
//		modelAndView.addObject("exception", ex);
//		System.out.println("[出异常了]:"+ex);
//		return modelAndView;
//	}
	
	
	/**
	 * 由 @InitBinder 标识的方法 ,可以对 WebDataBinder 对象进行初始化
	 * 1).@InitBinder方法不能有返回值，它必须声明为void
	 * 2).@InitBinder方法的参数通常是是 WebDataBinder
	 */
//	@InitBinder
//	public void testInitBinder(WebDataBinder binder) {
//		binder.setDisallowedFields("username");
//	}
	
	
	/**
	 * 1. 有 @ModelAttribute 标记的方法, 会在每个目标方法执行之前被 SpringMVC 调用! 
	 * 2. 有 @ModelAttribute 注解也可以来修饰目标方法 POJO 类型的入参, 其 value 属性值有如下的作用:
	 * 1). SpringMVC 会使用 value 属性值在 implicitModel 中查找对应的对象, 若存在则会直接传入到目标方法的入参中.
	 * 2). SpringMVC 会以 value 为 key, POJO 类型的对象为 value, 存入到 request 中. 
	 */
	@ModelAttribute
	public void getUser(@RequestParam(value="id",required=false) Integer id, 
			Map<String, Object> map){
		if(id != null){
			//模拟从数据库中获取对象
			User user = new User(id, "bbb", "bbb", "bb@bbb.com", 12);
			map.put("user", user);
		}
	}
	
	
	
	
}
