package tw.sunny.finalproject.module;


import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
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
    public static final String MEDIA = "Media";


    public String doHttpPost(String path, Map<String, String> map) throws InternetException {
        return doHttpConnect(path, POST, map);
    }

    public String doHttpPost(String urlServer, String filePath) throws InternetException {
        HttpURLConnection connection = null;
        DataOutputStream outputStream = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary =  "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1*1024*1024;
        String pathToOurFile = filePath;
        File file = new File(pathToOurFile);
        int serverResponseCode = 0;
        String serverResponseMessage = "";
        try
        {
            FileInputStream fileInputStream = new FileInputStream(file);
            URL url = new URL(urlServer);
            connection = (HttpURLConnection) url.openConnection();

            // Allow Inputs &amp; Outputs.
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            // Set HTTP method to POST.
            connection.setRequestMethod("POST");

            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
//            connection.setRequestProperty("Content-Type", "multipart/form-data");

            outputStream = new DataOutputStream( connection.getOutputStream() );
            outputStream.writeBytes(twoHyphens + boundary + lineEnd);
            outputStream.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + file.getName() +"\"" + lineEnd);
            outputStream.writeBytes(lineEnd);

            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // Read file
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0)
            {
                outputStream.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            outputStream.flush();
            outputStream.close();
            fileInputStream.close();

            // Responses from the server (code and message)
            serverResponseCode = connection.getResponseCode();
            serverResponseMessage = convertToString(connection.getInputStream());



            if(serverResponseCode == 200)
                return serverResponseMessage;



        }
        catch (Exception ex)
        {
            //Exception
            serverResponseCode = 999;
            serverResponseMessage = "Unhandled Exception";
        }
        throw new InternetException(serverResponseCode, serverResponseMessage);
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
            throw new InternetException(connection.getResponseCode(), convertToString(connection.getErrorStream()));
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