package com.arcansecurity.skeerel.util;

import com.arcansecurity.skeerel.util.json.JSONException;
import com.arcansecurity.skeerel.util.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author Florian Pradines
 */
public final class Request {

    private Request() {}

    public static JSONObject getJson(String url, Map<String, String> parameters) throws IOException {
        URL obj = new URL(url + mapToQuery(parameters));
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.getResponseCode();

        BufferedReader in;
        if (con.getResponseCode() >= 200 && con.getResponseCode() < 300) {
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        } else {
            in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
        }
        StringBuilder response = new StringBuilder();

        for (String inputLine; (inputLine = in.readLine()) != null; ) {
            response.append(inputLine);
        }
        in.close();

        try {
            return new JSONObject(response.toString());
        } catch (JSONException e) {
            throw new IOException(e);
        }
    }

    private static String mapToQuery(Map<String, String> parameters) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> parameter : parameters.entrySet()) {
            if (0 == sb.length()) {
                sb.append("?");
            } else {
                sb.append("&");
            }

            sb.append(URLEncoder.encode(parameter.getKey(), "UTF-8"));
            sb.append("=");
            sb.append(URLEncoder.encode(parameter.getValue(), "UTF-8"));
        }

        return sb.toString();
    }
}
