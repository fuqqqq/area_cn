package com.fuqqqq.common.area.cn.controller;

import cn.hutool.core.util.StrUtil;
import com.fuqqqq.common.area.cn.controller.base.BaseController;
import com.fuqqqq.common.area.cn.data.model.TAreaModel;
import com.fuqqqq.common.area.cn.data.service.TAreaService;
import com.fuqqqq.common.area.cn.define.R;
import com.fuqqqq.common.area.cn.entity.resp.AreaResp;
import com.fuqqqq.common.area.cn.service.CrawlService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@RequestMapping("/area")
@RestController
public class AreaController extends BaseController {
    @Resource
    private TAreaService tAreaService;
    @Resource
    private CrawlService crawlService;


    @GetMapping("/getAll")
    public Mono<R<List<AreaResp>>> getAll() {
        return Mono
                .fromCallable(() -> {
                    List<AreaResp> data = AreaResp.fromModels(
                            tAreaService.findAll().toArray(TAreaModel[]::new)
                    );
                    return R.success(data);
                })
                .subscribeOn(Schedulers.boundedElastic());
    }


    @GetMapping("/getChildren")
    public Mono<R<List<AreaResp>>> getChildren(@RequestParam(required = false) String parentCode) {
        return Mono
                .fromCallable(() -> {
                    int level = 1;
                    String codePrefix = null;
                    if (StrUtil.isNotBlank(parentCode)) {
                        TAreaModel parentModel = tAreaService.findOne(parentCode);
                        level = parentModel.getLevel() + 1;
                        int len = switch (parentModel.getLevel()) {
                            case 1 -> 2;
                            case 3 -> 4;
                            default -> parentModel.getCode().length();
                        };
                        codePrefix = StrUtil.sub(parentModel.getCode(), 0, len);
                    }
                    List<AreaResp> data = AreaResp.fromModels(
                            tAreaService.findByLevel(level, codePrefix).toArray(TAreaModel[]::new)
                    );
                    return R.success(data);
                })
                .subscribeOn(Schedulers.boundedElastic());
    }


    @PostMapping("/crawl")
    public Mono<R<Void>> crawl() {
        return Mono
                .fromCallable(() -> {
                    crawlService.crawl();
                    return R.success();
                })
                .subscribeOn(Schedulers.boundedElastic());
    }
}
