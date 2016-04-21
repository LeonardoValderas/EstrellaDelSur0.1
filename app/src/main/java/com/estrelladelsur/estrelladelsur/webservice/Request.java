package com.estrelladelsur.estrelladelsur.webservice;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class Request {

    String uri;
    String query = null;
    String method = "GET";
    Map<String, String> parametros = new HashMap<>();

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, String> getParametros() {
        return parametros;
    }

    public void setParametros(Map<String, String> parametros) {
        this.parametros = parametros;
    }

    public void setParametrosDatos(String key, String value) {
        parametros.put(key, value);
    }

    public String getEncodedParams() {
        StringBuilder sb = new StringBuilder();

        for (String key : parametros.keySet()) {
            String value = null;
            try {
                value = URLEncoder.encode(parametros.get(key), "UTF-8");

            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(key + "=" + value);
        }
        return sb.toString();
    }

}