package com.fuqqqq.common.area.cn.service.impl;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.fuqqqq.common.area.cn.config.CrawlProperties;
import com.fuqqqq.common.area.cn.data.model.TAreaModel;
import com.fuqqqq.common.area.cn.data.service.TAreaService;
import com.fuqqqq.common.area.cn.service.CrawlService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.ExecutorService;

@Slf4j
@Service
public class CrawlServiceImpl implements CrawlService {
    @Resource
    private ExecutorService scheduledThreadPool;
    @Resource
    private TAreaService tAreaService;
    @Resource
    private CrawlProperties crawlProperties;
    @Resource
    private RestTemplate restTemplate;

    private Object lock = null;

    @Override
    public void crawl() {
        if (lock != null) {
            return;
        }
        scheduledThreadPool.submit(() -> {
            lock = new Object();
            try {
                tAreaService.clear();

                int size = 0;

                List<TAreaModel> list1 = crawlLevel1();
                tAreaService.insertBatch(list1.toArray(TAreaModel[]::new));
                size += list1.size();

                for (TAreaModel model1 : list1) {
                    List<TAreaModel> list2 = crawlLevel2(model1.getCode());
                    tAreaService.insertBatch(list2.toArray(TAreaModel[]::new));
                    size += list2.size();

                    for (TAreaModel model2 : list2) {
                        List<TAreaModel> list3 = crawlLevel3(model2.getCode());
                        tAreaService.insertBatch(list3.toArray(TAreaModel[]::new));

                        for (TAreaModel model3 : list3) {
                            log.info(
                                    "--> --> Crawl Progress: ({}): [{}]{}-{}-{}",
                                    ++size, model3.getCode(),
                                    model1.getName(), model2.getName(), model3.getName()
                            );
                        }
                    }
                }

                log.info(">>>>>>>>>> Crawl Done <<<<<<<<<<");

            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                lock = null;
            }
        });
    }


    //----- Private Methods ------------------------------------------------------------------------------


    private List<TAreaModel> crawlLevel1() {
        String url = crawlProperties.getRootUrl() + "index.html";
        String html = restTemplate.getForObject(url, String.class);

        String regex = crawlProperties.getRegex().get(0);
        List<String> listGroup0 = ReUtil.findAllGroup0(regex, html);

        return listGroup0.stream()
                .map(group0 -> {
                    String codeCut = ReUtil.replaceAll(group0, regex, "$1");
                    String code = StrUtil.fill(codeCut, '0', 6, false);
                    String name = ReUtil.replaceAll(group0, regex, "$2");
                    return TAreaModel.builder()
                            .code(code)
                            .name(name)
                            .level(1)
                            .build();
                })
                .toList();

    }

    private List<TAreaModel> crawlLevel2(String parentCode) {
        String codeCut = StrUtil.subPre(parentCode, 2);
        String url = crawlProperties.getRootUrl() + codeCut + ".html";
        String html = restTemplate.getForObject(url, String.class);

        String regex = crawlProperties.getRegex().get(1);
        List<String> listGroup0 = ReUtil.findAllGroup0(regex, html);

        return listGroup0.stream()
                .map(group0 -> {
                    String code = ReUtil.replaceAll(group0, regex, "$2");
                    String name = ReUtil.replaceAll(group0, regex, "$5");
                    return TAreaModel.builder()
                            .code(code)
                            .name(name)
                            .level(2)
                            .build();
                })
                .toList();
    }

    private List<TAreaModel> crawlLevel3(String parentCode) {
        String codeCut1 = StrUtil.subPre(parentCode, 2);
        String codeCut2 = StrUtil.subPre(parentCode, 4);
        String url = crawlProperties.getRootUrl() + codeCut1 + "/" + codeCut2 + ".html";
        String html = restTemplate.getForObject(url, String.class);

        String regex = crawlProperties.getRegex().get(2);
        List<String> listGroup0 = ReUtil.findAllGroup0(regex, html);

        return listGroup0.stream()
                .map(group0 -> {
                    String code = ReUtil.replaceAll(group0, regex, "$2");
                    String name = ReUtil.replaceAll(group0, regex, "$5");
                    return TAreaModel.builder()
                            .code(code)
                            .name(name)
                            .level(3)
                            .build();
                })
                .toList();
    }
}
