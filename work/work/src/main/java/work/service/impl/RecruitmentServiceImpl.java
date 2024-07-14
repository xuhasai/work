package work.service.impl;

import org.springframework.stereotype.Service;
import work.entity.RecruitmentUser;
import work.mapper.RecruitmentUserMapper;
import work.service.RecruitmentService;

import javax.annotation.Resource;
import java.util.List;
@Service
public class RecruitmentServiceImpl implements RecruitmentService {
    @Resource
    RecruitmentUserMapper  recruitmentUserMapper;
    @Override
    public List<RecruitmentUser> getAllRecruitment(int start, int end) {
        return recruitmentUserMapper.getAllRecruitment(start,end);
    }

    @Override
    public void deleteRecruitment(List<String> ids) {
        recruitmentUserMapper.deleteRecruitment(ids);
    }

    @Override
    public void updateRecruitment(RecruitmentUser recruitmentUser) {
        recruitmentUserMapper.updateRecruitment(recruitmentUser);
    }
}
