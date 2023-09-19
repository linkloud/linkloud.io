package io.linkloud.api.domain.openGraph.api;

import io.linkloud.api.domain.openGraph.dto.OpenGraphDto;
import io.linkloud.api.domain.openGraph.service.OpenGraphService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v2/opengraph")
@RestController
public class OpenGraphController {

    private final OpenGraphService openGraphService;

    @GetMapping
    public ResponseEntity<OpenGraphDto> getOpenGraph(@RequestParam String targetUrl) {
        log.info("OpenGraph URL Request : {}",targetUrl);
        OpenGraphDto responseDto = openGraphService.getOpenGraphData(targetUrl);
        return ResponseEntity.ok(responseDto);
    }

}
