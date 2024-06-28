package top.michive.iplab.service;

import top.michive.iplab.entity.IPOriginalFailData;

import java.util.List;

public interface IPOriginalFailDataService {

    IPOriginalFailData getById(Long id);

    List<IPOriginalFailData> getByPage(IPOriginalFailData ipOriginalFailData, Integer page, Integer offset);

    IPOriginalFailData insert(IPOriginalFailData ipOriginalFailData);

    IPOriginalFailData unformattedAdd(String ipOriginalFailData);
}
