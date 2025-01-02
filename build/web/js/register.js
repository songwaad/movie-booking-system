$(document).ready(function() {
    $('#register-form').on('submit', function(event) {
        event.preventDefault(); // ป้องกันการรีเฟรชหน้า
        console.log("Form submitted"); // ตรวจสอบว่า event submit ถูกเรียก

        const formData = {
            email: $('#regisEmail').val(),
            password: $('#regisPassword').val(),
            fname: $('#fname').val(),
            lname: $('#lname').val(),
            phoneNo: $('#phoneNo').val(),
            role: $('#role').val(),
            action: 'register'
        };

        console.log("Data to send:", formData);

        $.ajax({
            url: '/Losther_JSP/api/user',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function(response) {
                console.log('ลงทะเบียนสำเร็จ:', response);
                alert('Registration successful!');
                window.location.href = 'index.jsp';
            },
            error: function(xhr, status, error) {
                console.error('Registration failed:', error);
            }
        });
    });
});
