package quartifex.com.navigaze;

public class ApiUtils {
	public static final String BASE_URL = "https://accessibility-cloud.freetls.fastly.net/place-infos.json?excludeSourceIds=LiBTS67TjmBcXdEmX&x=19490&y=24495&z=13&appToken=27be4b5216aced82122d7cf8f69e4a07&locale=en_US&includePlacesWithoutAccessibility=0";

	public static ResponseService getResponseService() {
		return RetrofitClient.getClient(BASE_URL)
				.create(ResponseService.class);
	}
}