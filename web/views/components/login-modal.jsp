<!-- Login-Modal -->
<div class="login-modal" id="login-modal">
    <div class="content">
        <span id="close-login-modal-btn" class="close">&times;</span>
        <form id="login-form">
            <h2>LOSTHER</h2>
            <input type="email" id="email" placeholder="Email" required>
            <input type="password" id="password" placeholder="Password" required>
            <button class="submitBtn" type="submit">LOGIN</button>
            <p>Don't have an account? <span id="switchToRegister"> Register here</span></p>
        </form>

        <form id="register-form" style="display: none;">
            <h2>LOSTHER</h2>
            <input type="email" id="regisEmail" placeholder="Email" required value="email@email.com">
            <input type="password" id="regisPassword" placeholder="password" required value="12345">
            <input type="text" id="fname" placeholder="Firstname" required value="firtname">
            <input type="text" id="lname" placeholder="Lastname" required value="lastname">
            <input type="tel" id="phoneNo" placeholder="Phone Number" required value="02-000-0000">
            <input type="hidden" id="role" value="customer">
            <button class="submitBtn" type="submit">REGISTER</button>
            <p>Already have an account? <span id="switchToLogin">Login here</span></p>
        </form>

    </div>
</div>