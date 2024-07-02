package top.michive.iplab.controller;

import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.michive.iplab.entity.IPFailSummarize;
import top.michive.iplab.service.IPFailSummarizeService;

import java.util.List;

@RestController
@RequestMapping("/info")
public class IPInfoController {

    @Resource
    private IPFailSummarizeService ipFailSummarizeService;

    @GetMapping
    public ResponseEntity<List<IPFailSummarize>> getByPage(IPFailSummarize ipFailSummarize, @RequestParam(defaultValue = "1") Integer page, Integer offset) {
        return ResponseEntity.ok(this.ipFailSummarizeService.getByPage(ipFailSummarize, page, offset));
    }

    @GetMapping("/blacklist")
    public ResponseEntity<String> getNeedToBlock(@RequestParam(defaultValue = "10") Integer limit) {
        return ResponseEntity.ok(this.ipFailSummarizeService.getNeedToBlock(limit));
    }
}
