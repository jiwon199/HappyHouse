package com.ssaffy.happyapi.news;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
 
 
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
 

@RestController
@RequestMapping("/news")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class NewsController {
	 
	 
	@GetMapping(value = "/getNews/{address}")
	public JSONObject getNews(@PathVariable("address") String aptName) {
		String jsonStr=searchNews(aptName ,"https://openapi.naver.com/v1/search/news?display=6&sort=sim&query=");
		JSONParser parser = new JSONParser();
		Object obj = null;
		try {
			obj = parser.parse( jsonStr );
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject jsonObj = (JSONObject) obj;
        return jsonObj;
        
        
	}
	
	@GetMapping(value = "/getImage/{address}")
	public JSONObject getImage(@PathVariable("address") String aptName) {
		String jsonStr=searchNews("아파트"+aptName ,"https://openapi.naver.com/v1/search/image?display=1&sort=sim&query=");
		 
		JSONParser parser = new JSONParser();
		Object obj = null;
		try {
			obj = parser.parse( jsonStr );
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject jsonObj = (JSONObject) obj;
        return jsonObj;
        
        
	}
	
	
	//네이버 뉴스 검색 api
	//네이버 보안 정책으로 인해 서버 프로그래밍에서 호출해야 함
	public String searchNews(String searchWord,String url) {
        String text = null;
        try {
            text = URLEncoder.encode(searchWord, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패",e);
        }

        String apiURL = url + text ;    // json 결과
        //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // xml 결과

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", "클라키");
        requestHeaders.put("X-Naver-Client-Secret", "시크릿키");
        String responseBody = get(apiURL,requestHeaders);
       // System.out.println(responseBody);
        
        return responseBody;
    }
	private String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 에러 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }
    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }
	
}
