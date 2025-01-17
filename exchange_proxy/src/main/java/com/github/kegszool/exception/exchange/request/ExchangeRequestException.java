package com.github.kegszool.exception.exchange.request;

public class ExchangeRequestException extends RuntimeException {

    public ExchangeRequestException(String msg) {
        super(msg);
    }

    public ExchangeRequestException(String msg, Throwable ex) { super(msg, ex); }
}