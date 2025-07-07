package com.org.kodvix.redbooks.service;

import com.org.kodvix.redbooks.dto.*;

public interface AuthenticationService {
    void registerCustomer(RegisterCustomerRequest req);
    void registerSchool(RegisterSchoolRequest req);
    void registerPublisher(RegisterPublisherRequest req);
    AuthResponse authenticate(AuthRequest req);

    AuthResponse changeEmail(ChangeEmailRequest req, String currentUserEmail);
    void changePassword(ChangePasswordRequest req, String currentUserEmail);

    void forgotPassword(ForgotPasswordRequest req);
    void verifyOtpAndResetPassword(VerifyOtpRequest req);
}
