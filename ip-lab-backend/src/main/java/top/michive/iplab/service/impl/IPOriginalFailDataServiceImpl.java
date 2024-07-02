package top.michive.iplab.service.impl;

import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import top.michive.iplab.dao.IPOriginalFailDataDao;
import top.michive.iplab.entity.IPOriginalFailData;
import top.michive.iplab.service.IPOriginalFailDataService;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static top.michive.iplab.service.impl.IPOriginalFailDataServiceImpl.Type.*;


@Service("ipOriginalFailDataService")
public class IPOriginalFailDataServiceImpl implements IPOriginalFailDataService {
    @Resource
    private IPOriginalFailDataDao ipOriginalFailDataDao;

    private final Logger logger = LoggerFactory.getLogger(IPOriginalFailDataServiceImpl.class);

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
    public List<IPOriginalFailData> formatAndAdd(String ipOriginalFailData) {
        var rows = ipOriginalFailData.split("\\R");
        var tl = new ArrayList<Thread>(50);
        for (var i : rows) {

            var cols = i.split(" +", 4);
            if (cols.length < 4) {
                continue;
            }
            logger.debug("{}", Arrays.toString(cols));
            tl.add(Thread.ofVirtual().start(() -> {
                var data = new IPOriginalFailData();
                for (var j = 0; j < cols.length; j++) {
                    switch (identifyCol(cols[j], j)) {
                        case IP -> data.setIp(cols[j]);
                        case TTY -> data.setTty(cols[j]);
                        case DATE -> {
                            var date = cols[j].replace("  ", " ").split(" -")[0];//TODO better format
                            var localDate = MonthDay.parse(date, formatter);
                            var localTime = LocalTime.parse(date, formatter);
                            data.setDate(Date.from(localDate.atYear(LocalDateTime.now().getYear()).atTime(localTime).atZone(ZoneId.systemDefault()).toInstant()));
                        }
                        case LOGIN_NAME -> data.setLoginName(cols[j]);
                    }
                }
                logger.debug("parsed {}", data);
                ipOriginalFailDataDao.insert(data);

            }));

            if (tl.size() >= 35) {
                for (var j : tl) {
                    try {
                        j.join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                tl.clear();
            }
        }


        return null;
    }

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM d HH:mm[[:ss zzz yyyy]]", Locale.ENGLISH);
    private static final Type[] mappedType = new Type[]{LOGIN_NAME, TTY, IP, DATE, UNKNOWN};

    private Type identifyCol(String ignoredData, int index) {
        //TODO: identify each col by data
        logger.debug("get data {} at {}", ignoredData, index);
        return mappedType[index];
    }

    enum Type {
        IP, DATE, LOGIN_NAME, TTY, UNKNOWN
    }
}
