package br.com.fatec.server.responses;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlRootElement()
public class SuccessResponse {

    private Integer statusCode;
    private Boolean success;
    private Object data;

    public SuccessResponse(Object data) {
        this.statusCode = 200;
        this.success = true;
        this.data = data;
    }
}
