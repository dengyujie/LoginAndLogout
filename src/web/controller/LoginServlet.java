package web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.User;
import service.BusinessService;
import service.impl.BusinessServiceImpl;

public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		BusinessService service = new BusinessServiceImpl();
		User user = service.loginUser(username, password);
		if(user != null){
			request.getSession().setAttribute("user", user);//��user�浽session��
			request.setAttribute("message", "��ϲ"+user.getUsername()+"��½�ɹ�����ҳ�潫��5�����ת����ҳ<meta http-equiv='refresh' content='5;url=/LoginAndLogout/index.jsp'");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
		else{
			request.setAttribute("message", "�Բ����û������������");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
