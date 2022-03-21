package br.com.fatec.server.responses;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
@XmlRootElement(name = "error")
public class ErrorResponse {
    private Integer statusCode;
    private Boolean success;
    private String error;

    public ErrorResponse(String error, HttpStatus statusCode) {
        this.statusCode = statusCode.value();
        this.success = false;
        this.error = error;
    }
}