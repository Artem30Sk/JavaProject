package com.example.cakeclient;

import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import retrofit.RestAdapter;
import retrofit.client.Client;

public class RestService {
    private static final String URL="https://cakeclientapi.conveyor.cloud/api/";
    private RestAdapter restAdapter;
    private UserService userService;
    public RestService()
    {
            restAdapter=new RestAdapter.Builder()
                    .setEndpoint(URL)
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .build();
            userService=restAdapter.create(UserService.class);
    }
    public UserService getUserService(){
        return userService;
    }
}
