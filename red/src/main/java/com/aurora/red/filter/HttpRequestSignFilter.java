package com.aurora.red.filter;

import com.aurora.red.util.Signature;
import org.springframework.core.annotation.Order;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * TODO
 *
 * @author shaopengwei
 * @version 1.0.0
 * @since 2021/08/09 13:57
 */
@Order(1) // 过滤器优先级，x 值越小优先级越高
@WebFilter(urlPatterns = "/*")
public class HttpRequestSignFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {
    if (servletRequest.getParameterMap().isEmpty() || !servletRequest.getParameterMap().containsKey("sign")) {
      servletRequest.getRequestDispatcher("/error/paramerror").forward(servletRequest, servletResponse);
      return;
    }
    Map<String, String[]> params = new HashMap(servletRequest.getParameterMap());
    String requestSign = params.get("sign")[0];
    params.remove("sign");
    String mySign = Signature.genSign(params);
    if (!mySign.equals(requestSign)) {
      servletRequest.getRequestDispatcher("/error/signerror").forward(servletRequest, servletResponse);
      return;
    }
    filterChain.doFilter(servletRequest, servletResponse);
  }

  @Override
  public void destroy() {

  }
}
