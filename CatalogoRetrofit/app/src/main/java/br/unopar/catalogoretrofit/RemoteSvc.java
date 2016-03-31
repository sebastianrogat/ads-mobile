package br.unopar.catalogoretrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RemoteSvc {
    @GET("api/mercadoria")
    Call<List<Mercadoria>> recuperaMercadorias();

   /* @POST("api/mercadoria")
    void inserir(@Body Mercadoria mercadoria);*/
}
