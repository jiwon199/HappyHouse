package com.ssaffy.happyapi.User;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
	@Autowired
	UserService service;
	
	@Autowired
	JavaMailSender javaMailSender;
	
	private static final String secretKey =  "secretKey";
	
	public String createToken(UserDto userDto){
	    Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.DATE,1); //만료일 1일

	    Claims claims = Jwts.claims()
	        .setSubject("access_token")
	        .setIssuedAt(new Date())
	        .setExpiration(new Date(cal.getTimeInMillis()));

	    claims.put("id", userDto.getId());
	    claims.put("name", userDto.getName());
	    claims.put("addr", userDto.getAddr());
	    claims.put("tel", userDto.getTel());

	    String jwt = Jwts.builder()
	        .setHeaderParam("typ", "JWT")
	        .setClaims(claims)
	        .signWith(SignatureAlgorithm.HS256,  secretKey.getBytes())
	        .compact();

	    return jwt;
	}
	
	@GetMapping(value = "/getTokenInfo")
	public Claims loadTokenValue(HttpServletRequest request) {
		String jwt = request.getHeader("Authentication");
		Claims claims = Jwts.parser()
            .setSigningKey(secretKey.getBytes())
            .parseClaimsJws(jwt).getBody();
		return claims;
	}
	
	@PostMapping(value = "/login")
	public String login(@RequestBody HashMap<String, String> map) throws Exception {
		UserDto userDto = service.login(map);
		if (userDto != null) {
			String token = createToken(userDto);
			return token;			
		} else {
			return "0";
		}
	}
	@PostMapping(value = "/register")
	public void register(@RequestBody HashMap<String, String> map) {
		service.register(map);
	}
	@GetMapping(value = "/myPage")
	public Object myPage(HttpServletRequest request) {
		Claims claims = loadTokenValue(request);
        return claims;
	}
	@PostMapping(value = "/edit")
	public void edit(@RequestBody HashMap<String, String> map) {
		service.edit(map);
	}
	@PostMapping(value = "/delete")
	public void delete(@Value("${file.paths}") String filePath, @RequestBody String id) {
		service.delete(id);
		File file = new File(filePath + "\\" + id + ".png");
		if (file.exists() == true) {
			file.delete();
		}
	}
	@PostMapping(value = "/confirmId")
	public String confirmId(@RequestBody String id) {
		UserDto userDto = service.confirmId(id);
		if (userDto == null) return "1";
		else return "0";
	}
	@PostMapping(value = "/getLikeArea")
	public List<String> getLikeArea(@RequestBody String id) {
		List<String> list = service.getLikeArea(id);
		return list;
	}
	@PostMapping(value = "/getDongInfo")
	public DongDto getDongInfo(@RequestBody String dongCode) {
		DongDto dongDto = service.getDongInfo(dongCode);
		return dongDto;
	}
	@PostMapping(value = "/delLike")
	public void delLike(@RequestBody HashMap<String, String> map) {
		service.delLikeArea(map);	
	}
	@GetMapping(value="/isAdmin/{id}")
	public int isAdmin(@PathVariable("id") String id) {
		return service.isAdmin(id);
	}
	@GetMapping(value="/userList")
	public List<UserDto> userList() {
		return service.userList();
	}
	@GetMapping(value="/promotion/{id}")
	public void promotion(@PathVariable("id") String id) {
		service.promotion(id);
	}
	@PostMapping(value="/writeNotice")
	public void writeNotice(@RequestBody HashMap<String, String> map) {
//		System.out.println(map);
		service.writeNotice(map);
	}
	@GetMapping(value="/getNotice")
	public List<NoticeDto> getNotice() {
		return service.getNotice();
	}
	@GetMapping(value="/noticeDelete/{num}")
	public void noticeDelete(@PathVariable("num") int num) {
		service.noticeDelete(num);
	}
	@PostMapping(value="/mailing")
	public void mailing(@RequestBody HashMap<String, String> map) throws MessagingException{
		System.out.println("메일 보내기를 시도합니다.");
		MimeMessage message = javaMailSender.createMimeMessage();
		message.setSubject("해피하우스 비밀번호 찾기 서비스입니다.");
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(map.get("addr")));
		message.setText("비밀번호는 " + map.get("pw") + "입니다.\n다음부터 까먹으면 혼내겠습니다. 잘 관리하세요");
		message.setSentDate(new Date());
		javaMailSender.send(message);
		System.out.println("메일 발송 완료!!");
	}
	@PostMapping(value="/idEmail")
	public pwDto idEmail(@RequestBody HashMap<String, String> map) {
//		System.out.println(map);
		pwDto bean = new pwDto();
		UserDto userDto = service.idEmail(map);
		if (userDto == null) {
			bean.setStatus(0);
		} else if (!userDto.getAddr().equals(map.get("addr"))) {
			bean.setStatus(1);
		} else {
			bean.setStatus(2);
			bean.setPw(userDto.getPw());
		}
		return bean;
	}
	@PostMapping(value="/sss")
	public void sss(Session session) {
		System.out.println(session.toString());
	}
}
