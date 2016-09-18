package tw.sunny.finalproject.module;


import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 網路相關底層功能
 *
 * @author nagi
 * @version 1.0
 */
public class InternetModule {
    public static final String GET = "GET";
    public static final String POST = "POST";


    public String doHttpPost(String path, Map<String, String> map) throws InternetException {
        return doHttpConnect(path, POST, map);
    }

    public String doHttpGet(String path) throws InternetException {
        return doHttpConnect(path, GET, null);
    }

    public static String getParameterStringEncode(Map<String, String> map, String encode) {
        String param = null;
        try {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (param == null)
                    param = entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), encode);
                else
                    param += "&" + entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), encode);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return param;
    }

    private String doHttpConnect(String path, String method, Map<String, String> map) throws InternetException {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(path);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.setDoOutput(true);
            connection.setRequestMethod(method);
            connection.setUseCaches(false);
            if (map != null && !map.isEmpty()) {
                connection.setDoInput(true);
                OutputStream os = connection.getOutputStream();
                DataOutputStream dos = new DataOutputStream(os);
                dos.writeBytes(getParameterStringEncode(map, "utf-8"));
                dos.flush();
                dos.close();
            }
            if (connection.getResponseCode() == 200)
                return convertToString(connection.getInputStream());
            throw new InternetException(connection.getResponseCode(), convertToString(connection.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new InternetException(999, "Unhandled exception.");
    }

    private static String convertToString(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        String result = "";
        if (inputStream != null) {
            try {
                while ((len = inputStream.read(data)) != -1) {
                    outputStream.write(data, 0, len);
                }
                result = new String(outputStream.toByteArray(), "utf-8");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }



    public interface InternetCallback {
        void onSuccess(String data);
        void onFail(String msg);
    }



    public class InternetException extends Exception {


        private int errorCode;

        public InternetException(int errorCode, String detailMessage) {
            super(detailMessage);
            this.errorCode = errorCode;
        }

        public int getErrorCode() {
            return errorCode;
        }

        @Override
        public String toString() {
            return "[" + errorCode + "] " + getMessage();
        }
    }

}