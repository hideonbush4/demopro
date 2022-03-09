package com.example.demo.config;

import com.example.demo.domain.Response;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @author dengzhewen
 * @create 2022-03-09 14:22
 * @Version v1.0.0
 */
@RestControllerAdvice
public class ExceptionAdvice {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    // 增加对校验异常的抛出
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Response handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.error(e.getMessage(), e);
        return Response.fail(this.getValidMessage(e.getBindingResult().getAllErrors()), (Object) null, 1000);
    }

    private String getValidMessage(List<ObjectError> errors) {
        return (String)errors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(","));
    }
}
