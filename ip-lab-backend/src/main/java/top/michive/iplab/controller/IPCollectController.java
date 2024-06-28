package top.michive.iplab.controller;

import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.michive.iplab.entity.IPOriginalFailData;
import top.michive.iplab.service.IPOriginalFailDataService;

@RestController
@RequestMapping("/collect")
public class IPCollectController {
    @Resource
    private IPOriginalFailDataService ipOriginalFailDataService;

    @PostMapping
    public ResponseEntity<IPOriginalFailData> add(@RequestBody IPOriginalFailData ipOriginalFailData) {
        if (ipOriginalFailData == null
                || ipOriginalFailData.getIp() == null
                || ipOriginalFailData.getTty() == null
                || ipOriginalFailData.getLoginName() == null
                || ipOriginalFailData.getDate() == null) {

            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(this.ipOriginalFailDataService.insert(ipOriginalFailData));
    }

    @PostMapping("/unformatted")
    public ResponseEntity<IPOriginalFailData> unformattedAdd(@RequestBody String ipOriginalFailData) {

        return ResponseEntity.ok(this.ipOriginalFailDataService.unformattedAdd(ipOriginalFailData));
    }
}
