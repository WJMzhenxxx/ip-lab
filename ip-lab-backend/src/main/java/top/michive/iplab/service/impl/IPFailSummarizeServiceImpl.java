package top.michive.iplab.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.michive.iplab.dao.IPFailSummarizeDao;
import top.michive.iplab.entity.IPFailSummarize;
import top.michive.iplab.service.IPFailSummarizeService;

import java.util.List;

@Service("ipFailSummarizeService")
public class IPFailSummarizeServiceImpl implements IPFailSummarizeService {
    @Resource
    private IPFailSummarizeDao ipFailSummarizeDao;

    @Override
    public IPFailSummarize getByIP(String ip) {
        return null;
    }

    @Override
    public List<IPFailSummarize> getByPage(IPFailSummarize ipFailSummarize, Integer page, Integer limit) {

        return (this.ipFailSummarizeDao.getAllByLimit(ipFailSummarize, limit == null ? null : (page - 1) * limit, limit));
    }

    @Override
    public String getNeedToBlock(Integer limit) {
        var ips = ipFailSummarizeDao.getTopN(limit);
        var rez = new StringBuilder();
        ips.forEach(e -> rez.append("sshd:").append(e.getIp()).append(":deny").append("\n"));
        return rez.toString();
    }
}
