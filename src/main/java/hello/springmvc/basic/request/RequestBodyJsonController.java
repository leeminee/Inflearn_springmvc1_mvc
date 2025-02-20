package hello.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyJsonController {

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/request-body-jason-v1")
    public void requestBodyJasonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={} ", messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={}, age={} ", helloData.getUsername(), helloData.getAge());
        response.getWriter().write("ok");
    }

    @ResponseBody
    @PostMapping("/request-body-jason-v2")
    public String requestBodyJasonV2(@RequestBody String messageBody) throws IOException {
        log.info("messageBody={} ", messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={}, age={} ", helloData.getUsername(), helloData.getAge());
        return "ok";
    }

    @ResponseBody
    @PostMapping("/request-body-jason-v3")
    public String requestBodyJasonV3(@RequestBody HelloData helloData) {
        log.info("username={}, age={} ", helloData.getUsername(), helloData.getAge());
        return "ok";
    }

    @ResponseBody
    @PostMapping("/request-body-jason-v4")
    public String requestBodyJasonV4(HttpEntity<HelloData> data) {
        HelloData helloData = data.getBody();
        log.info("username={}, age={} ", helloData.getUsername(), helloData.getAge());
        return "ok";
    }

    @ResponseBody
    @PostMapping("/request-body-jason-v5")
    public HelloData requestBodyJasonV5(@RequestBody HelloData helloData) {
        log.info("username={}, age={} ", helloData.getUsername(), helloData.getAge());
        return helloData;
    }
}
