package work.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import work.entity.RecruitmentUser;
import work.service.RecruitmentService;

import java.util.List;

@RestController
public class RecruitmentController {

    @Autowired
    RecruitmentService recruitmentService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @RequestMapping("/getAllRecruitment")
    public List<RecruitmentUser> getAllRecruitment(int start, int end){
        return recruitmentService.getAllRecruitment(start,end);
    }

    @RequestMapping("/deleteRecruitment")
    public void deleteRecruitment(@RequestBody List<String> deleteId){
        if(deleteId.size()>0){
            recruitmentService.deleteRecruitment(deleteId);
        }
    }
    @RequestMapping("/updateRecruitment")
    public void updateRecruitment(@RequestBody RecruitmentUser recruitmentUser){
        if(recruitmentUser.getUsername() != null && !recruitmentUser.getUsername().isEmpty()){
            if(recruitmentUser.getPassword() != null && !recruitmentUser.getPassword().isEmpty()){
                recruitmentUser.setPassword(passwordEncoder.encode(recruitmentUser.getPassword()));
                recruitmentService.updateRecruitment(recruitmentUser);
            }else{
                recruitmentUser.setPassword(passwordEncoder.encode("123456"));
                recruitmentService.updateRecruitment(recruitmentUser);
            }
        }else{
            return;
        }
    }
}
