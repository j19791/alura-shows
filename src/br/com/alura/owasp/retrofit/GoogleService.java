package br.com.alura.owasp.retrofit;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GoogleService {

	@POST("siteverify") // Os parâmetros secret/response devem ser passados ao Google por meio de uma
						// requisição POST
						// para o endereço siteverify
	Call<Resposta> enviaToken(@Query("secret") String secret, @Query("response") String response);
}
