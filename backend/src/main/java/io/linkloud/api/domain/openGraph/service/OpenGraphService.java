package io.linkloud.api.domain.openGraph.service;

import io.linkloud.api.domain.openGraph.dto.OpenGraphDto;
import io.linkloud.api.global.exception.CustomException;
import io.linkloud.api.global.exception.ExceptionCode.LogicExceptionCode;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OpenGraphService {

    private static final String OG_TITLE = "meta[property=og:title]";
    private static final String OG_IMAGE = "meta[property=og:image]";
    private static final String OG_URL = "meta[property=og:url]";
    private static final String OG_DESCRIPTION = "meta[property=og:description]";
    private static final String CONTENT = "content";

    // TODO : 외부 라이브러리 사용해서 느림 캐시 적용해야 됨
    public OpenGraphDto getOpenGraphData(String targetUrl) {
        try {
            Document doc = Jsoup.connect(targetUrl).get();
            String title = doc.select(OG_TITLE).attr(CONTENT);
            String image = doc.select(OG_IMAGE).attr(CONTENT);
            String url = doc.select(OG_URL).attr(CONTENT);
            String description = doc.select(OG_DESCRIPTION).attr(CONTENT);
            log.info("OpenGraph Request Success");
            log.info("OpenGraph title = {}",title);
            log.info("OpenGraph image = {}",image);
            log.info("OpenGraph url = {}",url);
            log.info("OpenGraph description = {}",description);

            return OpenGraphDto.builder()
                .title(title)
                .description(description)
                .ogImage(image)
                .url(url)
                .build();

        } catch (IOException e) {
            log.error("잘못된 openGraph URL: {}",targetUrl);
            throw new CustomException(LogicExceptionCode.BAD_REQUEST);
        }
    }

}
