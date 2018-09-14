package com.springmvc.userlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("userlistService")
public class UserListService {
	
	@Autowired
	@Qualifier("userlistDao")
	private UserListDao userlistDao;
	
	

}
