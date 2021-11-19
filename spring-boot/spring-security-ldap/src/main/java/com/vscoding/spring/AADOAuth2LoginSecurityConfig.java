package com.vscoding.spring;

import com.azure.spring.aad.webapp.AADWebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;


@Component
public class AADOAuth2LoginSecurityConfig extends AADWebSecurityConfigurerAdapter {

  /**
   * Add configuration logic as needed.
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    super.configure(http);
    http.authorizeRequests().anyRequest().access("hasRole('Redirect_User_Access')");
  }
}
