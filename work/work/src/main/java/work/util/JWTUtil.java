package work.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class JWTUtil {
    //Jwt秘钥
    private static final String key = "abcdefg";
    @Resource
    StringRedisTemplate stringRedisTemplate;
    //根据用户信息创建Jwt令牌
    public String createJwt(UserDetails user){
        String uuid = UUID.randomUUID().toString();
        Algorithm algorithm = Algorithm.HMAC256(key);
        List list = new ArrayList(user.getAuthorities());
        List<String> list2 = new ArrayList<String>();
        for(int i=0;i<list.size();i++){
            list2.add(list.get(i)+"");
        }
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        Date expDate = new Date(date.getTime()+30*1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(simpleDateFormat.format(date));
        System.out.println(simpleDateFormat.format(expDate));
        String jwt = JWT.create()
                        .withClaim("name", user.getUsername())  //配置JWT自定义信息
                        .withClaim("authorities", list2)
                        .withExpiresAt(expDate)  //设置过期时间
                        .withIssuedAt(calendar.getTime())    //设置创建创建时间
                        .sign(algorithm);   //最终签名
        try {
            //验证token
            JWT.require(Algorithm.HMAC256(key)).build().verify(jwt);
        } catch (TokenExpiredException e) {
            System.out.println("msg"+"Token已经过期!!!");
        } catch (SignatureVerificationException e){
            System.out.println("msg"+ "签名错误!!!");
        } catch (AlgorithmMismatchException e){
            System.out.println("msg"+"加密算法不匹配!!!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("msg"+"无效token~~");
        }
        stringRedisTemplate.opsForValue().set(uuid,jwt,7, TimeUnit.DAYS);
        return uuid;
    }

    //根据Jwt验证并解析用户信息
    private static void resolveJwt(String token, HttpServletRequest request){
        try {
            DecodedJWT jj = JWT.decode(token);
            Map<String, Claim> claims = jj.getClaims();  //获取令牌中内容
            UserDetails userDetails = User.withUsername(claims.get("name").asString()).password("******").authorities(claims.get("authorities").asArray(String.class)).build();
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            //然后直接把配置好的Authentication塞给SecurityContext表示已经完成验证
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (JWTVerificationException e) {

        }
    }

    public void verify(String uuid,HttpServletRequest request){
        if(uuid != null && !uuid.isEmpty()){
            if(stringRedisTemplate.hasKey(uuid)){
                String token = stringRedisTemplate.opsForValue().get(uuid);
                resolveJwt(token,request);
            }
        }
    }

    public boolean signOut(String uuid){
        if(stringRedisTemplate.hasKey(uuid)){
            return stringRedisTemplate.delete(uuid);
        }else {
            return false;
        }
    }


}
