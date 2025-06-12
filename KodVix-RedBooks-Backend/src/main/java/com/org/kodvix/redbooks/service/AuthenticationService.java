package com.org.kodvix.redbooks.service;
import com.org.kodvix.redbooks.dto.*;

public interface AuthenticationService {
    AuthResponse registerCustomer(RegisterCustomerRequest req);
    AuthResponse registerSchool(RegisterSchoolRequest req);
    AuthResponse registerPublisher(RegisterPublisherRequest req);
    AuthResponse authenticate(AuthRequest req);
}
