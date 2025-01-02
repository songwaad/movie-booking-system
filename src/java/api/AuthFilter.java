package api;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/views/*")
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // สามารถใส่โค้ดตั้งค่าที่ต้องการ (ถ้ามี)
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

         HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        String requestURI = req.getRequestURI();

        // ตรวจสอบว่าถ้า URL เป็น index.jsp ก็ให้ข้าม filter นี้ไป
        if (requestURI.endsWith("/Losther_JSP/")) {
            chain.doFilter(request, response); // ข้าม filter
            return;
        }

        // ตรวจสอบว่ามี userId ใน session หรือไม่
        if (session == null || session.getAttribute("userId") == null) {
            res.sendRedirect(req.getContextPath() + "/"); // redirect ไปที่หน้า index
            return;
        }

        chain.doFilter(request, response); // ดำเนินการต่อถ้าล็อกอินแล้ว
    }

    @Override
    public void destroy() {
        // ใส่โค้ดล้างข้อมูลหรือปิดการเชื่อมต่อ (ถ้ามี)
    }
}
