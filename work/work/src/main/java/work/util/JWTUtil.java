package work.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
        Algorithm algorithm = Algorithm.HMAC256(key);
        List list = new ArrayList(user.getAuthorities());
        List<String> list2 = new ArrayList<String>();
        for(int i=0;i<list.size();i++){
            list2.add(list.get(i)+"");
        }
        Date date = new Date();
        Date expDate = new Date(date.getTime()+60*60*1000);
        String token = JWT.create()
                //.withClaim("password",user.getPassword())
                .withClaim("name", user.getUsername())  //配置JWT自定义信息
                .withClaim("authorities", list2)
                .withExpiresAt(expDate)  //设置过期时间
                .withIssuedAt(date)    //设置创建创建时间
                .sign(algorithm);   //最终签名
        stringRedisTemplate.opsForValue().set(token, token,60, TimeUnit.MINUTES);
        return token;
    }

    //根据Jwt验证并解析用户信息
    public boolean verify(String token,HttpServletRequest request){
        try {
            if(stringRedisTemplate.hasKey(token)){
                token = stringRedisTemplate.opsForValue().get(token);
                //验证token
                JWT.require(Algorithm.HMAC256(key)).build().verify(token);
                return true;
            }else {
                return false;
            }
        } catch (TokenExpiredException e) {
            System.out.println("msg"+"Token已经过期!!!");
            return false;
        } catch (SignatureVerificationException e){
            System.out.println("msg"+ "签名错误!!!");
            return false;
        } catch (AlgorithmMismatchException e){
            System.out.println("msg"+"加密算法不匹配!!!");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("msg"+"无效token~~");
            return false;
        }
    }

    public boolean signOut(String token){
        if(stringRedisTemplate.hasKey(token)){
            return stringRedisTemplate.delete(token);
        }else {
            return false;
        }
    }


}
