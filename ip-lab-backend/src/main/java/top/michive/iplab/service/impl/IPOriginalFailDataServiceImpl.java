package top.michive.iplab.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.michive.iplab.dao.IPOriginalFailDataDao;
import top.michive.iplab.entity.IPOriginalFailData;
import top.michive.iplab.service.IPOriginalFailDataService;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static top.michive.iplab.service.impl.IPOriginalFailDataServiceImpl.Type.*;


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
        var ipOriginalFailDataList = new ArrayList<IPOriginalFailData>();
        for (var i : rows) {
            var cols = i.split("  +");
            var data = new IPOriginalFailData();
            for (var j = 0; j < cols.length; j++) {
                switch (identifyCol(cols[j], j)) {
                    case IP -> data.setIp(cols[j]);
                    case TTY -> data.setTty(cols[j]);
                    case DATE -> {
                        var date = cols[j].split(" -")[0];//TODO format
                        var localDate = MonthDay.parse(date, formatter);
                        var localTime = LocalTime.parse(date, formatter);
                        data.setDate(Date.from(localDate.atYear(LocalDateTime.now().getYear()).atTime(localTime).atZone(ZoneId.systemDefault()).toInstant()));
                    }
                    case LOGIN_NAME -> data.setLoginName(cols[j]);
                }
            }
            System.out.println(data);
        }
        return null;
    }

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm[[:ss zzz yyyy]]", Locale.ENGLISH);
    private static final Type[] mappedType = new Type[]{LOGIN_NAME, TTY, IP, DATE, UNKNOWN};

    private Type identifyCol(String data, int index) {
        //TODO: identify each col by data
        return mappedType[index];
    }

    enum Type {
        IP, DATE, LOGIN_NAME, TTY, UNKNOWN
    }
}
