package com.andri.loteriadoifsul;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("sorteio/{tipo}")
    Call<Sorteio> getSorteio(@Path("tipo") String tipo);
}
