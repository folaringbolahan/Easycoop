/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.stereotype.Service;

//@Service("userDetailsService")
public class CustomUserDetailsService extends JdbcDaoImpl {
    
    @Override
	public void setUsersByUsernameQuery(String usersByUsernameQueryString) {
		super.setUsersByUsernameQuery(usersByUsernameQueryString);
	}

	@Override
	public void setAuthoritiesByUsernameQuery(String queryString) {
		super.setAuthoritiesByUsernameQuery(queryString);
	}
/*
	@Autowired
	private DataSource dataSource;

	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
	}
        //Override
        //public void setDataSource(DataSource dataSource) {
        //    this.dataSource = dataSource;
        //}
	
	@Override
	@Value("select userid as username, password, enabled,accountNonExpired,credentialsNonExpired,accountNonLocked from users where userid=?")
	public void setUsersByUsernameQuery(String usersByUsernameQueryString) {
		super.setUsersByUsernameQuery(usersByUsernameQueryString);
	}
	
	@Override
	@Value("select a.userid as username,c.menurole as role from users a inner join usergrpmdl b on a.accesslevel = b.usergroup and a.branch = b.branchid and a.companyid = b.companyid inner join modulemenu c on b.menu = c.menucode  where userid=?")
	public void setAuthoritiesByUsernameQuery(String queryString) {
		super.setAuthoritiesByUsernameQuery(queryString);
	}
*/
	//override to get accountNonLocked  
	@Override
	public List<UserDetails> loadUsersByUsername(String username) {
	  return getJdbcTemplate().query(super.getUsersByUsernameQuery(), new String[] { username },
		new RowMapper<UserDetails>() {
		  public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
			String username = rs.getString("email");
			String password = rs.getString("password");
			boolean enabled = rs.getBoolean("enabled");
			boolean accountNonExpired = rs.getBoolean("accountNonExpired");
			boolean credentialsNonExpired = rs.getBoolean("credentialsNonExpired");
			boolean accountNonLocked = rs.getBoolean("accountNonLocked");

			return new User(username, password, enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, AuthorityUtils.NO_AUTHORITIES);
		  }

	  });
	}

	//override to pass accountNonLocked
	@Override
	public UserDetails createUserDetails(String username, UserDetails userFromUserQuery,
			List<GrantedAuthority> combinedAuthorities) {
		String returnUsername = userFromUserQuery.getUsername();

		if (super.isUsernameBasedPrimaryKey()) {
		  returnUsername = username;
		}

		return new User(returnUsername, userFromUserQuery.getPassword(), 
                       userFromUserQuery.isEnabled(),
		       userFromUserQuery.isAccountNonExpired(), 
                       userFromUserQuery.isCredentialsNonExpired(),
			userFromUserQuery.isAccountNonLocked(), combinedAuthorities);
	}
}
