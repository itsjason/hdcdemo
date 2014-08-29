package net.hdc.hdcdemoapp.services;

import android.util.Log;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

public class RestClient {

    private Header[] responseHeaders;
    private InputStream instream;

    public enum RequestMethod {POST, GET, PUT}

    private ArrayList<NameValuePair> params;
    private ArrayList<NameValuePair> headers;

    private String url;

    private int responseCode;
    private String message;

    private String response;

    public String getResponse() {
        return response;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public RestClient(String url) {
        this.url = url;
        params = new ArrayList<NameValuePair>();
        headers = new ArrayList<NameValuePair>();
        //this.context = context;
    }

    public void AddParam(String name, String value) {
        params.add(new BasicNameValuePair(name, value));
    }

    public void AddParam(String name, int value) {
        params.add(new BasicNameValuePair(name, Integer.toString(value)));
    }

    public void AddHeader(String name, String value) {
        headers.add(new BasicNameValuePair(name, value));
    }

    public void Execute(RequestMethod method) throws Exception {
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("URL", this.url);
        parameters.put("Method", method.name());

        Log.d("REST", "Making call to: " + this.url);

        switch (method) {
            case GET: {
                sendGet();
                break;
            }

            case POST: {
                sendPost();
                break;
            }
            case PUT: {
                throw new Exception("Not Implemented");
            }
        }

        //FlurryAgent.endTimedEvent(Events.REST_REQUEST);
    }

    private void sendPost() throws Exception {
        HttpPost request = new HttpPost(url);
        for (NameValuePair h : headers) {
            request.addHeader(h.getName(), h.getValue());
        }

        if (!params.isEmpty()) {
            request.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        }

        executeRequest(request, url);
    }

    private void sendGet() throws Exception {
        //add parameters
        String combinedParams = "";
        if (!params.isEmpty()) {
            combinedParams += "?";
            for (NameValuePair p : params) {
                String paramString = p.getName() + "=" + URLEncoder.encode(p.getValue(), "UTF-8");
                if (combinedParams.length() > 1) {
                    combinedParams += "&" + paramString;
                } else {
                    combinedParams += paramString;
                }
            }
        }

        HttpGet request = new HttpGet(url + combinedParams);

        //add headers
        for (NameValuePair h : headers) {
            request.addHeader(h.getName(), h.getValue());
        }

        executeRequest(request, url);
    }



    private void executeRequest(HttpUriRequest request, String url) throws Exception {
        Log.v("REST", "Sending Request....");

        HttpClient client = new DefaultHttpClient();

        HttpResponse httpResponse;

        try {
            httpResponse = client.execute(request);
            responseCode = httpResponse.getStatusLine().getStatusCode();
            Log.v("REST", "HTTP Response Code: " + responseCode);
            message = httpResponse.getStatusLine().getReasonPhrase();
            Log.v("REST", "HTTP Message: " + message);
            HttpEntity entity = httpResponse.getEntity();

            responseHeaders = httpResponse.getAllHeaders();

            if (entity != null) {

                instream = entity.getContent();

                boolean isImage = false;
                for (Header header : responseHeaders) {
                    if (!header.getName().equalsIgnoreCase("Content-Type")) continue;
                    Log.v("REST", "Content Type: " + header.getValue());
                    if (header.getValue().contains("image")) isImage = true;
                }

                if (!isImage)
                    response = readFully(instream);
                else
                    Log.v("REST", "Got image response.");

                Log.v("REST", "HTTP Response: " + response);

                // Closing the input stream will trigger connection release
                if (!isImage)
                    instream.close();
            }

        } catch (ClientProtocolException e) {
            Log.e("REST", "ERROR: " + e.getLocalizedMessage());
            client.getConnectionManager().shutdown();
            throw e;
        } catch (IOException e) {
            Log.e("REST", "ERROR: " + e.getLocalizedMessage());
            client.getConnectionManager().shutdown();
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            Log.e("REST", "Other error: " + e.getLocalizedMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public String readFully(InputStream inputStream)
            throws IOException {
        return new String(readBytes(inputStream));
    }

    private byte[] readBytes(InputStream inputStream)
            throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            baos.write(buffer, 0, length);
        }
        return baos.toByteArray();
    }
}