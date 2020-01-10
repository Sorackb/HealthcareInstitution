package org.lucasbernardo.healthcareinstitution.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.lucasbernardo.healthcareinstitution.exception.UnauthorizedException;
import org.lucasbernardo.healthcareinstitution.model.HealthcareInstitution;
import org.lucasbernardo.healthcareinstitution.service.HealthcareInstitutionService;
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

  private final List<String> exclusions = new ArrayList<String>() {
    {
      add("/healthcareinstitution");
      add("/v2/api-docs");
      add("/configuration/ui");
      add("/swagger-resources");
      add("/configuration/security");
      add("/swagger-ui.html");
      add("/webjars/");
    }
  };

  @Autowired
  private HealthcareInstitutionService healthcareInstitutionService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
    try {
      final String path = request.getRequestURI();
      final Boolean exclude = exclusions.stream().anyMatch((exclusion) -> path.startsWith(exclusion));

      if (exclude) {
        chain.doFilter(request, response);
        return;
      }

      final String authorization = request.getHeader("Authorization");

      if (authorization == null || authorization.isEmpty()) {
        throw new UnauthorizedException("error", "Invalid Token.");
      }

      final String token = authorization.substring(7);

      HealthcareInstitution healthcareInstitution = this.healthcareInstitutionService.findByToken(token);

      request.setAttribute("owner", healthcareInstitution);

      chain.doFilter(request, response);
    } catch (Exception ex) {
      Logger.getLogger(AuthenticationRequestFilter.class.getName()).log(Level.SEVERE, null, ex);
      this.resolver.resolveException(request, response, null, ex);
    }
  }
}
