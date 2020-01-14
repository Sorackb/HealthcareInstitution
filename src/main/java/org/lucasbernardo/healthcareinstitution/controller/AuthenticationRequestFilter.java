package org.lucasbernardo.healthcareinstitution.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.lucasbernardo.healthcareinstitution.exception.UnauthorizedException;
import org.lucasbernardo.healthcareinstitution.service.HealthcareInstitutionService;
import org.lucasbernardo.healthcareinstitution.service.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 *
 * @author Lucas<sorackb@gmail.com>
 */
@Component
public class AuthenticationRequestFilter extends OncePerRequestFilter {

  @Autowired
  @Qualifier("handlerExceptionResolver")
  private HandlerExceptionResolver resolver;

  private final List<String> secured = Arrays.asList("/exams");

  @Autowired
  private TokenAuthenticationService tokenAuthenticationService;

  @Autowired
  private HealthcareInstitutionService healthcareInstitutionService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
    try {
      final String path = request.getRequestURI();
      Boolean validate = this.secured.stream().anyMatch(path::startsWith);
      String authorization;
      String token;
      String cnpj;

      if (Boolean.FALSE.equals(validate)) {
        chain.doFilter(request, response);
        return;
      }

      authorization = request.getHeader("Authorization");

      if (authorization == null || authorization.isEmpty()) {
        throw new UnauthorizedException("error", "The resource is secured and no token was informed.");
      }

      token = authorization.substring(7);
      cnpj = this.tokenAuthenticationService.decode(token);
      this.healthcareInstitutionService.checkCnpj(cnpj);
      request.setAttribute("cnpj", cnpj);
      chain.doFilter(request, response);
    } catch (Exception ex) {
      Logger.getLogger(AuthenticationRequestFilter.class.getName()).log(Level.SEVERE, null, ex);
      this.resolver.resolveException(request, response, null, ex);
    }
  }
}
