package work.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import work.entity.JobSeekersUser;
import work.entity.RecruitmentUser;
import work.service.JobSeekersService;

import java.util.List;

@RestController
public class JobSeekersController {
    @Autowired
    JobSeekersService jobSeekersService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @RequestMapping("/getAllJobSeekers")
    public List<JobSeekersUser> getAllRecruitment(int start, int end){
        return jobSeekersService.getAllJobSeekers(start,end);
    }

    @RequestMapping("/deleteJobSeekers")
    public void deleteRecruitment(@RequestBody List<String> deleteId){
        if(deleteId.size()>0){
            jobSeekersService.deleteJobSeekers(deleteId);
        }
    }

    @RequestMapping("/updateJobSeekers")
    public void updateJobSeekers(@RequestBody JobSeekersUser jobSeekersUser){
        if(jobSeekersUser.getUsername() != null && !jobSeekersUser.getUsername().isEmpty()){
            if(jobSeekersUser.getPassword() != null && !jobSeekersUser.getPassword().isEmpty()){
                jobSeekersUser.setPassword(passwordEncoder.encode(jobSeekersUser.getPassword()));
                jobSeekersService.updateJobSeekers(jobSeekersUser);
            }else{
                jobSeekersUser.setPassword(passwordEncoder.encode("123456"));
                jobSeekersService.updateJobSeekers(jobSeekersUser);
            }
        }else{
            return;
        }
    }
}
