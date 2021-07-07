import org.apache.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.*;
import org.apache.http.entity.mime.content.*;
import org.apache.http.impl.client.*;
import org.apache.http.util.EntityUtils;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SmmryExtractor {

	private static String convertStreamToString(java.io.InputStream is) {
	    java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}
	
	public static String extract(String url)
	{
		CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet request = new HttpGet("http://api.smmry.com/&SM_API_KEY=C5B4FB76E3" +
            		"&SM_LENGTH=3" + "&SM_IGNORE_LENGTH" + "&SM_QUOTE_AVOID" + "&SM_URL=" + url);

            CloseableHttpResponse response;

            String result = null;
            
            boolean noSmmry = false;

            //System.out.println("executing request " + request.getRequestLine());
            
            response = httpclient.execute(request);
            HttpEntity entity= response.getEntity();
            
            try {
                //System.out.println("----------------------------------------");
                //System.out.println(response.getStatusLine());

                if (entity != null) {
                    //System.out.println("Response content length: " + entity.getContentLength());
                }
                
                InputStream in = entity.getContent();
                
                String smmry = convertStreamToString(in);
                
                EntityUtils.consume(entity);
                
                
                int start = smmry.indexOf("\"sm_api_content\":\"") + 18;
                int end = smmry.indexOf("\",\"sm_api_limitation\":\"");
                
                if(start == -1 || end == -1)
                {	
                	noSmmry = true;
                }
                
                //System.out.println(smmry.substring(end));
                
                if(noSmmry)
                	return "No summary available";
                
                result = smmry.substring(start, end);
                result = result.replaceAll("\\\\", "");
                return result;
            } catch (Exception e){
            	e.printStackTrace();
            } finally {
                response.close();
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        return "No summary available";

	}

}
