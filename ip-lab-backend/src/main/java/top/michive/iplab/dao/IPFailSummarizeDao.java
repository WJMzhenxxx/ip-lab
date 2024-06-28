package top.michive.iplab.dao;

import top.michive.iplab.entity.IPFailSummarize;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPFailSummarizeDao {


    IPFailSummarize getByIP(String ip);

    List<IPFailSummarize> getAllByLimit(@Param("ip_fail_summarize") IPFailSummarize ipFailSummarize, @Param("offset") Integer offset, @Param("limit") Integer limit);

    long count(IPFailSummarize ipFailSummarize);

}

