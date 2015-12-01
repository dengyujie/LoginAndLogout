package service.impl;

import service.BusinessService;
import utils.DaoFactory;
import dao.UserDao;
import dao.impl.UserDaoXmlImpl;
import domain.User;

public class BusinessServiceImpl implements BusinessService {
	
	UserDao dao = DaoFactory.getInstance().createUserDao();//user dao factory to create a certain dao according to properties file
	
	//user register
	public void registerUser(User user){
		if(user != null){
			//checked exception ����ʱ�쳣������ȥ����Ҫ����
			//unchecked exception ����ʱ�쳣����һ�����账��
			//�����ױ���ʱ�쳣��ԭ��������һ�����������쳣���Ը��û�һ���Ѻõ���ʾ
			throw new RuntimeException("user already exist!");
		}
		dao.addUser(user);
	}
	
	//user log in
	public User loginUser(String username, String password){
		return dao.findUser(username, password);
	}
	
}
