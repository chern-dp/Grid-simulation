package rest;

import lombok.Data;

import java.io.Serializable;

@Data
public class RespArray implements Serializable {

    public int[][] response;
    public String errMsg;
    public RespArray(int[][] response){
        this.response = response;
        this.errMsg = null;

        if(response == null) {
            this.errMsg = "GridTooLarge";
            this.response = new int[][]{};
        }
    }
}