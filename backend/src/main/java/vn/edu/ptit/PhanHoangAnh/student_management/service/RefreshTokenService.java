package vn.edu.ptit.PhanHoangAnh.student_management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.RefreshTokenRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.RefreshToken;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    public void createRefreshToken(RefreshToken rf) {
        this.refreshTokenRepository.save(rf);
    }

    public RefreshToken findRefreshTokenByToken(String token) {
        return this.refreshTokenRepository.findByToken(token).orElseThrow(() -> new RuntimeException("Token not found"));
    }

    public void deleteRefreshTokenById(Long id) {
        RefreshToken refreshToken = this.refreshTokenRepository.findById(id).orElseThrow(() -> new RuntimeException("Token not found"));
        this.refreshTokenRepository.delete(refreshToken);
    }
}
