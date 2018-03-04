package net.tmarton.assignment;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * Filter enables CORS for all requests and any remote origin
 *
 * Created by tmarton on 09/02/2018.
 */
@Component
public class CorsFilter implements WebFilter {

    @Override
    public Mono<Void> filter(final ServerWebExchange serverWebExchange, final WebFilterChain webFilterChain) {

        // Adapted from https://sandstorm.de/de/blog/post/cors-headers-for-spring-boot-kotlin-webflux-reactor-project.html
        serverWebExchange.getResponse().getHeaders().add("Access-Control-Allow-Origin", "*");
        serverWebExchange.getResponse().getHeaders().add("Access-Control-Allow-Methods", "GET, POST");
        serverWebExchange.getResponse().getHeaders().add("Access-Control-Allow-Headers",
                "DNT,X-CustomHeader,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Content-Range,Range");
        serverWebExchange.getResponse().getHeaders().add("Access-Control-Expose-Headers",
                    "DNT,X-CustomHeader,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Content-Range,Range");
        return webFilterChain.filter(serverWebExchange);
    }
}
