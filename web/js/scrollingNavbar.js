document.addEventListener("scroll", () => {
    const navbar = document.getElementById("navbar");
    if (window.scrollY > 50) {
      // เปลี่ยนที่นี้เพื่อกำหนดระยะที่ต้องการ
      navbar.classList.add("scrolled");
    } else {
      navbar.classList.remove("scrolled");
    }
  });