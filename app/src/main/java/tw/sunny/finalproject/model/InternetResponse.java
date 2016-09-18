package tw.sunny.finalproject.model;

/**
 * Created by lixinting on 2016/9/18.
 */
public class InternetResponse {
    private int statusCode;
    private String content;

    public InternetResponse(int statusCode, String content) {
        this.statusCode = statusCode;
        this.content = content;
    }

    public String getContent() {
        return content;
    }


    public int getStatusCode() {
        return statusCode;
    }

}
