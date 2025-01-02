package utility;

public class Constants {
    public static final String SECRET_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJJc3N1ZXIiOiJJc3N1ZXIiLCJVc2VybmFtZSI6IkphdmFJblVzZSIsImV4cCI6MTczMTE0MzA1MiwiaWF0IjoxNzMxMTQzMDUyfQ.G8dJ9PzGfpx4KEbQEfycwLg-HNwodGoEN2rdq341qJQ\n"; // ควรใช้ค่าที่ซับซ้อน
    public static final long JWT_EXPIRATION = 30 * 60 * 1000; // 30 นาที
    public static final long REFRESH_EXPIRATION = 7 * 24 * 60 * 60 * 1000; // 7 วัน
}
