## Hango API

### Create Authentication data with a purpose

- To create a new account or reset a password, you need to create authentication data.
- Authentication data should be verified.
- Each authentication data has a specific purpose and an expiry time.

To create a new authentication data, you can use the following method:

```
HangoApi::createEmailAuth(emailAddress: String, purpose: EmailAuthPurpose)
```

This method returns an `EmailAuthResponse` and sends an OTP (One-Time Password) to the user. The client needs to provide the correct OTP value using the following method:

```
HangoApi::verifyEmailAuth(otpValue: String, authId: String): VerifyEmailAuthResponse
```

## Register new account
To create a new account client needs to give user info with payload and a verified auth data. 
```
Hango::registerNewAccount(payload: RegisterAccountPayload, authDataId: String): Result<Nothing, String>
```