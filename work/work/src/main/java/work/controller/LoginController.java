package work.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import work.entity.AdministratorUser;
import work.entity.ResutSet;
import work.mapper.AdministratorUserMapper;
import work.service.UserDetailsServiceFactory;
import work.util.DecryptPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import work.util.JWTUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    AdministratorUserMapper administratorUserMapper;

    @Autowired
    private UserDetailsServiceFactory userDetailsServiceFactory;

    @Resource
    JWTUtil jwtUtil;


    @PostMapping("/login")
    @ResponseBody
    public ResutSet login(HttpServletRequest request, HttpServletResponse response, @RequestParam("username")String username, @RequestParam("password")String password, @RequestParam("loginType")String loginType){
        if("admin".equals(loginType)){
            AdministratorUser administratorUser =  administratorUserMapper.getUserByName(username);
            if(administratorUser != null){
                if(!password.isEmpty() && password.length()>=1){
                    String dncryptPassword = DecryptPassword.decryptPassword(administratorUser.getPassword());
                    if(dncryptPassword.equals(password)){
                        SecurityContext context = SecurityContextHolder.getContext();
                        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(administratorUser, null,
                                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_admin"));  //手动创建一个UsernamePasswordAuthenticationToken对象，也就是用户的认证信息，角色需要添加ROLE_前缀，权限直接写
                        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        //Authentication authentication = authenticationManager.authenticate(token);
                        context.setAuthentication(token);  //手动为SecurityContext设定认证信息
                        ResutSet<AdministratorUser> resutSet = new ResutSet<AdministratorUser>();
                        resutSet.setMessage("登录成功");
                        resutSet.setStatus("200");
                        resutSet.setData(administratorUser);
                        return resutSet;
                    }
                }
            }else{
                return null;
            }
        }else if("jobseekers".equals(loginType)){
            return null;
        } else if ("recruitment".equals(loginType)) {
            return null;
        }else {
            return null;
        }
        return null;
        /*SecurityContext context = SecurityContextHolder.getContext();  //获取SecurityContext对象（当前会话肯定是没有登陆的）
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("Test", null,
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_admin"));  //手动创建一个UsernamePasswordAuthenticationToken对象，也就是用户的认证信息，角色需要添加ROLE_前缀，权限直接写
        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        //Authentication authentication = authenticationManager.authenticate(token);
        context.setAuthentication(token);  //手动为SecurityContext设定认证信息*/
    }


    @PostMapping("/perform_login")
    @ResponseBody
    public ResutSet login(@RequestParam String username, @RequestParam String password, @RequestParam String loginType) {
        try {
            UserDetailsService userDetailsService = userDetailsServiceFactory.getUserDetailsService(loginType);
            if(userDetailsService == null){
                ResutSet resutSet = new ResutSet();
                resutSet.setMessage("用户名密码错误");
                resutSet.setStatus(HttpStatus.UNAUTHORIZED+"");
                resutSet.setData(null);
                return resutSet;
                //return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
            }
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), password);
            Authentication authentication = authenticationManager.authenticate(authRequest);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtil.createJwt(userDetails);
            ResutSet<AdministratorUser> resutSet = new ResutSet<AdministratorUser>();
            resutSet.setMessage("登录成功");
            resutSet.setStatus("200");
            resutSet.setData(administratorUserMapper.getUserByName(userDetails.getUsername()));
            return resutSet;
        } catch (AuthenticationException e) {
            ResutSet resutSet = new ResutSet();
            resutSet.setMessage(e.getMessage());
            resutSet.setStatus(HttpStatus.UNAUTHORIZED+"");
            resutSet.setData(null);
            return resutSet;
            //return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
        }
    }

    @RequestMapping("/login3")
    public String login3(HttpServletRequest request, HttpServletResponse response){
        System.out.println("11111111111111111111111111111111");
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        //User user = (User)authentication.getPrincipal();
        return "/logout.html";
    }



}
