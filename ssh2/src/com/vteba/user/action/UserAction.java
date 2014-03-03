package com.vteba.user.action;

import java.util.Date;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.vteba.user.model.User;
import com.vteba.user.service.UserService;
import com.vteba.web.action.BaseAction;

/**
 * 用户Action.
 * @author yinlei
 * 2014-1-8 下午12:28:25
 */
public class UserAction extends BaseAction<User> {

	private static final long serialVersionUID = -711566328469110412L;
	private User model = new User();

	@Autowired
	private UserService userServiceImpl;
	@Autowired
	private JavaMailSender mailSenderImpl;
	
	@Override
	public User getModel() {
		return model;
	}

	@Override
	public String initial() throws Exception {
		//listResult = userServiceImpl.getAll(User.class);
		String hql = "select u from User u";
		listResult = userServiceImpl.getEntityListByHql(hql);
		
		MimeMessage mimeMessage = mailSenderImpl.createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
		
		helper.setFrom("kefu@eecn.com.cn");
		helper.setTo("yinlei@eecn.com.cn");
		
		mimeMessage.setSubject("kasdf好啊");
		mimeMessage.setText("尹雷阿斯顿发斯蒂芬", "UTF-8");
		
		//javaMailSenderImpl.send(mimeMessage);
		
		mailSenderImpl.send(mimeMessage);
		
		return SUCCESS;
	}
	
	public String add() throws Exception {
		
		return SUCCESS;
	}
	
	public String doAdd() throws Exception {
		model.setCreateDate(new Date());
		
		userServiceImpl.save(model);
		return SUCCESS;
	}
	
	public String detail() throws Exception {
		model = userServiceImpl.get(model.getId());
		return SUCCESS;
	}
	
	public String edit() throws Exception {
		model = userServiceImpl.get(model.getId());
		return SUCCESS;
	}
	
	public String update() throws Exception {
		userServiceImpl.saveOrUpdate(model);
		return SUCCESS;
	}
	
	public void delete() throws Exception {
		userServiceImpl.delete(model.getId());
		renderText("删除成功！");
	}
}
