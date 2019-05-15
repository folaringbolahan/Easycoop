/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.service;

import com.sift.votes.model.SaltedUser;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;

//@Service("userDetailsService")
public class VoterCustomUserDetailsService extends JdbcDaoImpl {
    //private String salt="";
    private Integer memberid;
    private Integer refid;
    String namedomainseperator = ":::::::";
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
            String drefid = "0";
            String username1 = "";
             String[] usernameAndDomain = StringUtils.split(username,namedomainseperator); //String.valueOf(Character.LINE_SEPARATOR));
             //System.out.println("username " + username);
             if (usernameAndDomain == null || usernameAndDomain.length != 2) {
                 drefid = "0";
             }
             if (usernameAndDomain.length == 2) {
                 drefid = usernameAndDomain[1];
                 username1 =usernameAndDomain[0];
             }
            // System.out.println("drefid " + drefid + " username1 " + username1);
	    //return getJdbcTemplate().query(super.getUsersByUsernameQuery(), new String[] { username },
            return getJdbcTemplate().query(super.getUsersByUsernameQuery(), new String[] { username1,drefid },
		new RowMapper<UserDetails>() {
		  public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
			String username3 = rs.getString("email");
			String password = rs.getString("password");
                        //String salt = rs.getString("salt");
			boolean enabled = rs.getBoolean("enabled");
			boolean accountNonExpired = rs.getBoolean("accountNonExpired");
			boolean credentialsNonExpired = rs.getBoolean("credentialsNonExpired");
			boolean accountNonLocked = rs.getBoolean("accountNonLocked");
                        memberid = rs.getInt("memberid");
                        refid = rs.getInt("agmid");
                       System.out.println("memberid " + memberid + " " + refid + " " + username3);
                        return new User(username3, password, enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, AuthorityUtils.NO_AUTHORITIES);                                          
			//return new SaltedUser(username, password, enabled, accountNonExpired, credentialsNonExpired,
			//	accountNonLocked, AuthorityUtils.NO_AUTHORITIES,salt);
                        
                     //
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
                
                String username1 = "";
                String[] usernameAndDomain = StringUtils.split(username,namedomainseperator); //String.valueOf(Character.LINE_SEPARATOR));
                if (usernameAndDomain.length == 2) {
                  username1 =usernameAndDomain[0];
                  returnUsername = username1;
                }

		return new User(returnUsername, userFromUserQuery.getPassword(), 
                       userFromUserQuery.isEnabled(),
		       userFromUserQuery.isAccountNonExpired(), 
                       userFromUserQuery.isCredentialsNonExpired(),
                        userFromUserQuery.isAccountNonLocked(), combinedAuthorities);
			//userFromUserQuery.isAccountNonLocked(), combinedAuthorities,((SaltedUser) userFromUserQuery).getSalt());
	}
        
        public Integer getMemberid() { return memberid; }
        public void setRefid(Integer refid) { this.refid = refid; }
        public Integer getRefid() { return refid; }
       // public void setSalt(String salt) { this.salt = salt; }
        
        //public String getSalt() { return salt; }
       // public void setSalt(String salt) { this.salt = salt; }
}
