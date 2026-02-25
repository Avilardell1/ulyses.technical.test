package com.septeo.ulyses.technical.test.component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

@Component
public class LoggingFilter extends OncePerRequestFilter {

    private static final String LOG_FILE = "ulyses_log.txt";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        long start = System.currentTimeMillis();
        LocalDateTime requestTime = LocalDateTime.now();

        filterChain.doFilter(request, response);

        long duration = System.currentTimeMillis() - start;

        String log = String.format(
                "%s | %s | %s | %d | %d ms%n",
                requestTime,
                request.getMethod(),
                request.getRequestURI(),
                response.getStatus(),
                duration
        );

        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            writer.print(log);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
