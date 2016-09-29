package tw.sunny.finalproject.module;

import android.os.AsyncTask;

import java.io.InputStream;
import java.util.Map;

import tw.sunny.finalproject.model.InternetResponse;

public class InternetTask extends AsyncTask<Void, Integer, InternetResponse> {
    private InternetModule.InternetCallback callback;
    private String url;
    private String method;
    private Map<String, String> data;
    private InternetModule module;
    private String filePath;

    public InternetTask(InternetModule.InternetCallback callback, String url, String method, Map<String, String> data) {
        this.callback = callback;
        this.url = url;
        this.method = method;
        this.data = data;
        module = new InternetModule();
    }

    public InternetTask(InternetModule.InternetCallback callback, String url, String filePath) {
        this.callback = callback;
        this.url = url;
        this.method = InternetModule.MEDIA;
        this.filePath = filePath;
        module = new InternetModule();
    }

    public InternetTask(InternetModule.InternetCallback callback, String url) {
        this.callback = callback;
        this.url = url;
        this.method = InternetModule.GET;
    }

    @Override
    protected InternetResponse doInBackground(Void... params) {
        String res = "";
        try {
            if(method.equals(InternetModule.GET))
                res = module.doHttpGet(url);
            else if(method.equals(InternetModule.POST))
                res = module.doHttpPost(url, data);
            else if(method.equals(InternetModule.MEDIA))
                res = module.doHttpPost(url, filePath);
            return new InternetResponse(200, res);
        } catch (InternetModule.InternetException e) {
            return new InternetResponse(e.getErrorCode(), e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(InternetResponse responses) {
        if(responses.getStatusCode() != 200) {
            callback.onFail(responses.getContent());
        } else {
            callback.onSuccess(responses.getContent());
        }
    }
}