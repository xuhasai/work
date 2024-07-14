package work.service;

import work.entity.Company;
import work.entity.RecruitmentUser;

import java.util.List;

public interface RecruitmentService {
    List<RecruitmentUser> getAllRecruitment(int start, int end);
    void deleteRecruitment(List<String> ids);
    void updateRecruitment(RecruitmentUser recruitmentUser);
}
