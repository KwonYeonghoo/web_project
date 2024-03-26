package com.example.web_project.config.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthenticationTypes {
    BadCredentialsException(401, "비밀번호가 일치하지 않습니다."),
    UsernameNotFoundException(402, "일치하는 계정이 존재하지 않습니다."),
    AccountExpiredException(403, "만료된 계정입니다."),
    CredentialsExpiredException(404,"만료된 비밀번호입니다."),
    DisabledException(405, "비활성화된 계정입니다."),
    LockedException(406, "잠겨있는 계정입니다."),
    NoneException(407, "알 수 없는 에러입니다.");

    private int code;
    private String msg;
}
