package view;


import java.io.*;
import java.net.HttpURLConnection;

import lombok.Getter;
import lombok.Setter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import javax.enterprise.context.SessionScoped;


import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.net.URL;

@Named
@SessionScoped
public class indexController implements Serializable {


    @Getter
    @Setter
    private boolean gridLarge = false;

    @Getter
    @Setter
    private int steps = 3;


    private static final String POST_URL = "http://localhost:8080/gomSpaceTest-1.0-SNAPSHOT/ap/v1/simulate/steps";

    public String getMessage() {
        return "Infinite grid simulator!";
    }

    //get the responce from server in the form of a String
    public String getGridString() {
        System.out.println("getString");

        //prepare JSON object to send to server
        JSONObject jsonInputString = new JSONObject();
        jsonInputString.put("steps", this.steps);
        jsonInputString.put("responseType", "String");

        String responce = null;

        try {

            Object resp = sendPUT(POST_URL, jsonInputString);


            //check if the server send back an error message and propagate instructions to user
            if (resp == null && this.gridLarge) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error: The Grid Could not be generated! Please try with lower number of steps", "Err: not enough memory:"));
                return "";

            } else if (resp == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error: no response from server!", "Err:"));

                return "";
            }

            //store in class variable
             responce = resp.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return responce;
    }

    public String[][] getGridArray() {

        //prepare json request
        JSONObject jsonInputString = new JSONObject();
        jsonInputString.put("steps", this.steps);
        jsonInputString.put("responseType", "Array");


        String[][] array = null;
        try {
            JSONArray jsonarr_1 = (JSONArray) sendPUT(POST_URL, jsonInputString);

            if (jsonarr_1 == null && this.gridLarge) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error: The Grid Could not be generated! Please try with lower number of steps", "Err: not enough memory:"));
                array = new String[][]{{""}};
                return array;

            } else if (jsonarr_1 == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error: no response from server!", "Err:"));
                array = new String[][]{{""}};
                return array;
            }


            String[][] tmpArr = new String[jsonarr_1.size()][((JSONArray) jsonarr_1.get(0)).size()];

            for (int i = 0; i < jsonarr_1.size(); i++) {

                JSONArray jsonarr_2 = (JSONArray) jsonarr_1.get(i);
                for (int j = 0; j < jsonarr_2.size(); j++) {
                    tmpArr[i][j] = ((Long) jsonarr_2.get(j)).toString();

                }

            }

            array = tmpArr;
        } catch (IOException e) {
        }

        return array;
    }

    //send the put request
    public Object sendPUT(String URL, JSONObject jsonInputString) throws IOException {
        System.out.println("PUT request!!");

        //open HTTPURLConnection and prepare request
        URL url = new URL(URL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("PUT");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);

        //send request
        try (OutputStream os = con.getOutputStream()) {
            os.write(jsonInputString.toString().getBytes("UTF-8"));
            os.flush();
        }

        int responseCode = con.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(response.toString());

                //check for an error message
                this.gridLarge = false;
                if (json.get("errMsg") != null && json.get("errMsg").equals("GridTooLarge")) {
                    this.gridLarge = true;
                    return null;
                }

                return json.get("response");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("POST request not worked");
        }
        return null;
    }


}
