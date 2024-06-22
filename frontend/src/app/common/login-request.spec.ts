import { LoginRequest } from './loginRequest';

describe('LoginRequest', () => {
  it('should create an instance', () => {
    const username = 'sampleUsername';
    const password = 'samplePassword';
    expect(new LoginRequest(username, password)).toBeTruthy();
  });
});
