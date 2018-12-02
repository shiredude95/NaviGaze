package quartifex.com.navigaze;

import quartifex.com.navigaze.POJO.ResponseObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ResponseService {

	@Multipart
	@GET("")
	Call<ResponseObject> json();

}
