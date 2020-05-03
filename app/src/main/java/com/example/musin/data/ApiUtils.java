package com.example.musin.data;

public class ApiUtils {
    private ApiUtils() {}

    public static final String BASE_URL = "https://la-musica.herokuapp.com/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
