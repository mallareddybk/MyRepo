package com.cybrilla.service.impl;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import com.cybrilla.dao.RoleDao;
import com.cybrilla.dao.UserDao;
import com.cybrilla.domain.User;
//import com.cybrilla.domain.security.UserRole;
import com.cybrilla.service.AccountService;
import com.cybrilla.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserDao userDao;
	
	/*@Autowired
	private RoleDao roleDao;*/
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public void save(User user) {
		userDao.save(user);
	}

	public User findByUsername(String username) {
		return userDao.findByUserName(username);
	}

	public User findByEmail(String email) {
		return userDao.findByEmail(email);
	}

	public User createUser(User user/*, Set<UserRole> userRoles*/) {
		User localUser = userDao.findByUserName(user.getUserName());

		if (localUser != null) {
			LOG.info("User with username {} already exist. Nothing will be done. ", user.getUserName());
		} else {
			String encryptedPassword = passwordEncoder.encode(user.getPassword());
			user.setPassword(encryptedPassword);

			/*for (UserRole ur : userRoles) {
				roleDao.save(ur.getRole());
			}
*/
			//user.getUserRoles().addAll(userRoles);

			user.setCurrentAccount(accountService.createCurrentAccount());
			user.setSavingsAccount(accountService.createSavingsAccount());

			localUser = userDao.save(user);
		}

		return localUser;
	}

	public boolean checkUserExists(String username, String email) {
		if (checkUsernameExists(username) || checkEmailExists(username)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean checkUsernameExists(String username) {
		if (null != findByUsername(username)) {
			return true;
		}

		return false;
	}

	public boolean checkEmailExists(String email) {
		if (null != findByEmail(email)) {
			return true;
		}

		return false;
	}

	public User saveUser(User user) {
		return userDao.save(user);
	}

	public void enableUser(String username) {
		User user = findByUsername(username);
		//user.setEnabled(true);
		userDao.save(user);
	}

	public void disableUser(String username) {
		User user = findByUsername(username);
		//user.setEnabled(false);
		//System.out.println(user.isEnabled());
		userDao.save(user);
		System.out.println(username + " is disabled.");
	}

	public List<User> findUserList() {
        return userDao.findAll();
    }
	
	
}
