package com.fuqqqq.common.area.cn.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fuqqqq.common.area.cn.define.R;
import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufMono;

@Slf4j
@Configuration
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {
    @Nonnull
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, @Nonnull Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        R<Void> r;
        if (ex instanceof HttpClientErrorException cli) {
            r = R.fial(cli.getStatusCode().value(), cli.getStatusText());

        } else if (ex instanceof RuntimeException rt) {
            r = R.fial(HttpStatus.BAD_REQUEST.value(), rt.getMessage());

        } else {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            r = R.fial(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        byte[] body;
        try {
            body = objectMapper.writeValueAsBytes(r);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        DataBuffer buf = response.bufferFactory().allocateBuffer(body.length).write(body);
        return response.writeAndFlushWith(Mono.just(ByteBufMono.just(buf)));
    }
}
