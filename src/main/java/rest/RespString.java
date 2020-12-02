package rest;

import lombok.Data;

import java.io.Serializable;

@Data
public class RespString implements Serializable {

    public String response;
    public String errMsg;
    public RespString(String response){
        this.response = response;
        this.errMsg = null;
        if(response == null) {
            this.errMsg = "GridTooLarge";
            this.response = "";
        }
    }
}