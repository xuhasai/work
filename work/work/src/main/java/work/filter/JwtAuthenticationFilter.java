package work.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import work.util.JWTUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

//继承OncePerRequestFilter表示每次请求过滤一次，用于快速编写JWT校验规则
    @Autowired
    JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication2 = context.getAuthentication();
        /*String requestURI = request.getRequestURI();
        String requestURL = request.getRequestURL().toString();
        String contextPath = request.getContextPath();
        String pathInfo = request.getPathInfo();*/
        /*System.out.println("Request URI: " + requestURI);
        System.out.println("Request URL: " + requestURL);
        System.out.println("Context Path: " + contextPath);
        System.out.println("Servlet Path: " + servletPath);
        System.out.println("Path Info: " + pathInfo);*/
        String servletPath = request.getServletPath();
        if("/login".equals(servletPath) || "/logout".equals(servletPath) || "/registered".equals(servletPath)) {
            filterChain.doFilter(request, response);
        }else {

            //首先从Header中取出JWT
            String authorization = request.getHeader("Authorization");
            if(authorization != null && !authorization.isEmpty() && authorization.length()>0){
                if(jwtUtil.verify(authorization,request)){
                    DecodedJWT token = JWT.decode(authorization);
                    Map<String, Claim> claims = token.getClaims();  //获取令牌中内容
                    UserDetails userDetails = User.withUsername(claims.get("name").asString()).password("******").authorities(claims.get("authorities").asArray(String.class)).build();
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    //然后直接把配置好的Authentication塞给SecurityContext表示已经完成验证
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            //可能各位小伙伴会好奇，要是没验证成功不是应该拦截吗？这个其实没有关系的
            //因为如果没有验证失败上面是不会给SecurityContext设置Authentication的，后面直接就被拦截掉了
            //而且有可能用户发起的是用户名密码登录请求，这种情况也要放行的，不然怎么登录，所以说直接放行就好
            filterChain.doFilter(request, response);
        }
    }
}
