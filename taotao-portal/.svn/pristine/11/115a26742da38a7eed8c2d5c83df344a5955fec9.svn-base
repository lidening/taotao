package httpclient;

import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class HttpClientTest {
	@Test
	public void doGet()throws Exception{
		CloseableHttpClient client=HttpClients.createDefault();
		HttpGet get=new HttpGet("http://localhost:8080/");
		CloseableHttpResponse response=client.execute(get);
		int statusCode = response.getStatusLine().getStatusCode();
		System.out.println(statusCode);
		HttpEntity entity = response.getEntity();
		String string = EntityUtils.toString(entity,"utf-8");
		System.out.println(string);
		response.close();
		client.close();
		
		
	}
	public void doGetWithParam() throws Exception{
		CloseableHttpClient client=HttpClients.createDefault();
		URIBuilder uriBuilder=new URIBuilder("http://www.sogou.com/web");
		uriBuilder.addParameter("qurey", "花千骨");
		HttpGet get=new HttpGet(uriBuilder.build());
		CloseableHttpResponse response=client.execute(get);
		int statusCode = response.getStatusLine().getStatusCode();
		System.out.println(statusCode);
		HttpEntity entity = response.getEntity();
		String string = EntityUtils.toString(entity,"utf-8");
		System.out.println(string);
		response.close();
		client.close();
	}
}
