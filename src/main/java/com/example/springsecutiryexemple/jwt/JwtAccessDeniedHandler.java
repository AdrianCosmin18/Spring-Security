package com.example.springsecutiryexemple.jwt;

import com.example.springsecutiryexemple.models.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import org.springframework.security.access.AccessDeniedException;

import static com.example.springsecutiryexemple.utils.Utils.ACCESS_DENIED_MESSAGE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;



@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    //autorizare corecta, dar lispa de permisiune
    //dupa ce am verificat daca am voie pe endpoint cu JwtAuthenticationEntryPoint si mi s-a dat un raspuns negativ => intra aici pe denied
    //adica m am autentificat corect dar nu am autoritate pe acest endpoint

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException{
        HttpResponse httpResponse = new HttpResponse(UNAUTHORIZED.value(), UNAUTHORIZED, UNAUTHORIZED.getReasonPhrase().toUpperCase(), ACCESS_DENIED_MESSAGE);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(UNAUTHORIZED.value());
        OutputStream outputStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(outputStream, httpResponse);
        outputStream.flush();
    }


}
