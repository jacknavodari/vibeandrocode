# Security Policy

## Supported Versions

We release patches for security issues. Which versions are eligible for security updates depends on the severity of the issue and the age of the release.

| Version | Supported          |
| ------- | ------------------ |
| 1.x.x   | :white_check_mark: |
| < 1.0   | :x:                |

## Reporting a Vulnerability

If you discover a security vulnerability within Mobile IDE, please send an email to [security@mobileide.example.com](mailto:security@mobileide.example.com) instead of using the public issue tracker.

Please include the following information in your report:

- A description of the vulnerability and its impact
- Steps to reproduce the vulnerability
- Affected versions of Mobile IDE
- Any known workarounds or mitigations

All security vulnerabilities will be promptly addressed. You will receive a response within 48 hours confirming receipt of your report. 

We appreciate your efforts to responsibly disclose your findings and will make every effort to acknowledge your contributions.

## Security Measures

Mobile IDE implements the following security measures:

### Data Protection
- API keys are stored securely using Android's DataStore
- No sensitive data is transmitted without encryption
- All API communications use HTTPS

### Permissions
- Minimal permissions required (INTERNET, READ/WRITE_EXTERNAL_STORAGE)
- Permissions are requested only when necessary
- Clear explanation of why each permission is needed

### Code Security
- Regular code reviews
- Dependency updates to address known vulnerabilities
- Secure coding practices following Android best practices

## Best Practices for Users

To ensure maximum security when using Mobile IDE:

1. Keep your API keys secure and regenerate them regularly
2. Use strong, unique passwords for AI service accounts
3. Keep the app updated to the latest version
4. Be cautious when sharing project files
5. Review and understand the permissions the app requests

## Security Updates

Security updates are released as needed. Critical security issues will be addressed immediately and released as patch updates. Users are encouraged to keep their apps updated to the latest version.