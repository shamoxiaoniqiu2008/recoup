package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import persistence.UserInfoMapper;
import domain.UserInfo;
import domain.UserInfoExample;

@Service
public class UserManageService {

	@Autowired
	private UserInfoMapper userInfoMapper;
	
	@Transactional
	public  void addUser(UserInfo user){
		
		userInfoMapper.insert(user);
		
	}
	
	@Transactional
	public  void deleteUser(UserInfo user){
		
		userInfoMapper.deleteByPrimaryKey(user.getUserId());
		
	}
	
	
	
	
	/**
	 * 查询全部用户
	 * @return
	 */
	public List<UserInfo> findAllUser(){
		UserInfoExample example = new UserInfoExample();
		example.or().andUserIdIsNotNull();
		return userInfoMapper.selectByExample(example);
	}
	
}
