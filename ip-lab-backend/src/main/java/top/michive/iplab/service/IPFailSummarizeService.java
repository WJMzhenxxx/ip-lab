package top.michive.iplab.service;

import top.michive.iplab.entity.IPFailSummarize;

import java.util.List;

public interface IPFailSummarizeService {

    IPFailSummarize getByIP(String ip);

    List<IPFailSummarize> getByPage(IPFailSummarize ipFailSummarize, Integer page, Integer offset);

}
