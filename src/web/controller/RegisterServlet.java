package web.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;

import domain.RegisterFormBean;
import domain.User;
import exception.UserExistException;
import service.BusinessService;
import service.impl.BusinessServiceImpl;
import utils.WebUtils;

public class RegisterServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		RegisterFormBean formbean = WebUtils.requestToBean(request, RegisterFormBean.class);
		if(formbean.isValidate() == false){
			request.setAttribute("formbean", formbean);//formbean�з�װ�˱�������Ϣ
			request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);//�������Դ�����Ϣ
			return;
		}
				
		try {
			//��formbean��䵽user��ȥ
			User user = new User();
			ConvertUtils.register(new DateLocaleConverter(), Date.class);//ע�� �ַ��������ڵ�ת����
			BeanUtils.copyProperties(user, formbean);
			user.setId(WebUtils.makeID());
			
			BusinessService service = new BusinessServiceImpl();
			service.registerUser(user);
			request.setAttribute("message", "ע��ɹ�����ҳ�潫��5�����ת����ҳ<meta http-equiv='refresh' content='5;url=/LoginAndLogout/index.jsp'");
			request.getSession().setAttribute("user", user);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			
		} catch(UserExistException e){ //����쳣������BusinessServiceImpl.java�е�registerUser�������׳���������Ҫ������д������û���ע��
			formbean.getErrors().put("username", "ע���û��Ѵ���");
			request.setAttribute("formbean", formbean);;
			request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);	
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "�Բ�����������ԭ��ע��ʧ��");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
