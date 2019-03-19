package br.com.alura.owasp.retrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInicializador {

	private Retrofit retrofit;

	private static final String BASE_URL = "https://www.google.com/recaptcha/api/";

	public RetrofitInicializador() {

		// descobrir como esta formatado o json retornado pelo google
		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();// intercepta requisicoes entre a aplicação e
																			// o Google
		interceptor.setLevel(Level.BODY);// nivel de interceptacao corpo

		OkHttpClient.Builder client = new OkHttpClient.Builder();
		client.addInterceptor(interceptor);

		retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()) // RETORNARA
				.client(client.build()) // JSON
				.build(); // construir o obj
	}

	public GoogleService getGoogleService() {
		return retrofit.create(GoogleService.class);

	}

}
