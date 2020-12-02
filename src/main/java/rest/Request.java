package rest;


import lombok.Data;

@Data
public class Request {
    private int steps;
    private String responseType;
    @Override
    public String toString(){
        return "Request{" +
                "steps=" + steps +
                "responseType=" + responseType +  '\'' +
                '}';

    }
}
