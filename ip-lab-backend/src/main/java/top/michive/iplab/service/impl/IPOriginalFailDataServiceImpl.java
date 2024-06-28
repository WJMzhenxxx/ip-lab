package top.michive.iplab.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.michive.iplab.dao.IPOriginalFailDataDao;
import top.michive.iplab.entity.IPOriginalFailData;
import top.michive.iplab.service.IPOriginalFailDataService;

import java.util.List;


@Service("ipOriginalFailDataService")
public class IPOriginalFailDataServiceImpl implements IPOriginalFailDataService {
    @Resource
    private IPOriginalFailDataDao ipOriginalFailDataDao;

    @Override
    public IPOriginalFailData getById(Long id) {
        return this.ipOriginalFailDataDao.getById(id);
    }

    @Override
    public List<IPOriginalFailData> getByPage(IPOriginalFailData ipOriginalFailData, Integer page, Integer limit) {

        return (this.ipOriginalFailDataDao.getAllByLimit(ipOriginalFailData, (page - 1) * limit, limit));
    }

    @Override
    public IPOriginalFailData insert(IPOriginalFailData ipOriginalFailData) {
        this.ipOriginalFailDataDao.insert(ipOriginalFailData);
        return ipOriginalFailData;
    }


    @Override
    public IPOriginalFailData unformattedAdd(String ipOriginalFailData) {
        var rows = ipOriginalFailData.split("\\R");
        for (var i : rows) {
            var cols = i.split("  +");
            for (var j : cols) {
                //TODO: identify each col
            }
        }
        return null;
    }
}
