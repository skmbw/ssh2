package com.vteba.user.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * 系统登录用户实体
 * @author yinlei
 * 2013-10-28 上午11:09:31
 */
@Entity
@Table(name = "user")
public class User implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	protected String id;
	protected String userName;
	protected String userAccount;
	protected String company;
	protected String password;
	protected Integer state;
	protected String mobilePhone;
	protected String telephone;
	protected String superAdmin;
	protected Date createDate;

	public User() {
	}

	public User(String id, String userName, String userAccount) {
		super();
		this.id = id;
		this.userName = userName;
		this.userAccount = userAccount;
	}

	public User(String userName, String userAccount, String company,
			String password, Integer state, String mobilePhone, String telephone) {
		this.userName = userName;
		this.userAccount = userAccount;
		this.company = company;
		this.password = password;
		this.state = state;
		this.mobilePhone = mobilePhone;
		this.telephone = telephone;
	}

	@Id
	@GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", unique = true, nullable = false)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "user_name", length = 100, nullable = false)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "user_account", length = 50, nullable = false)
	public String getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	@Column(name = "company", length = 500)
	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@Column(name = "password", length = 50, nullable = false)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "state")
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Column(name = "mobile_phone", length = 14, nullable = false)
	public String getMobilePhone() {
		return this.mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	@Column(name = "telephone", length = 20)
	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Column(name = "super_admin", length = 20)
	public String getSuperAdmin() {
		return superAdmin;
	}

	public void setSuperAdmin(String superAdmin) {
		this.superAdmin = superAdmin;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
