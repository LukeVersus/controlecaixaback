package com.omega.controlecaixa.domain.service.interfaces;

import com.omega.controlecaixa.domain.model.JwtRequest;
import com.omega.controlecaixa.domain.model.JwtResponse;

public interface LoginService {

    JwtResponse authenticate(JwtRequest authenticationRequest);

}
