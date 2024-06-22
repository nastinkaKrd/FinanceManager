import { AuthResponse } from './auth-response';

describe('AuthResponse', () => {
  it('should create an instance', () => {
    const accessToken = 'sampleAccessToken';
    const refreshToken = 'sampleRefreshToken';
    expect(new AuthResponse(accessToken, refreshToken)).toBeTruthy();
  });
});
