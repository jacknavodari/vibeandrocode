# Contributing to Mobile IDE

First off, thank you for considering contributing to Mobile IDE! It's people like you that make Mobile IDE such a great tool for developers.

## Code of Conduct

This project and everyone participating in it is governed by our Code of Conduct. By participating, you are expected to uphold this code. Please report unacceptable behavior to project maintainers.

## How Can I Contribute?

### Reporting Bugs

This section guides you through submitting a bug report for Mobile IDE. Following these guidelines helps maintainers and the community understand your report, reproduce the behavior, and find related reports.

**Before Submitting A Bug Report**
- Check the [issues list](https://github.com/yourusername/mobile-ide/issues) to see if the problem has already been reported
- Check if you're using the latest version of Mobile IDE
- Collect information about the bug:
  - Device model and Android version
  - Steps to reproduce the issue
  - Expected behavior
  - Actual behavior
  - Screenshots or screen recordings if possible

### Suggesting Enhancements

This section guides you through submitting an enhancement suggestion for Mobile IDE, including completely new features and minor improvements to existing functionality.

**Before Submitting An Enhancement Suggestion**
- Check the [issues list](https://github.com/yourusername/mobile-ide/issues) to see if the enhancement has already been suggested
- Check if you're using the latest version of Mobile IDE

### Your First Code Contribution

Unsure where to begin contributing to Mobile IDE? You can start by looking through these `beginner` and `help-wanted` issues:

- **Beginner issues** - issues which should only require a few lines of code, and a test or two.
- **Help wanted issues** - issues which should be a bit more involved than `beginner` issues.

### Pull Requests

The process described here has several goals:

- Maintain Mobile IDE's quality
- Fix problems that are important to users
- Engage the community in working toward the best possible Mobile IDE
- Enable a sustainable system for Mobile IDE's maintainers to review contributions

Please follow these steps to have your contribution considered by the maintainers:

1. Follow all instructions in [the template](PULL_REQUEST_TEMPLATE.md)
2. Follow the [styleguides](#styleguides)
3. After you submit your pull request, verify that all [status checks](https://help.github.com/articles/about-status-checks/) are passing

While the prerequisites above must be satisfied prior to having your pull request reviewed, the reviewer(s) may ask you to complete additional design work, tests, or other changes before your pull request can be ultimately accepted.

## Styleguides

### Git Commit Messages

- Use the present tense ("Add feature" not "Added feature")
- Use the imperative mood ("Move cursor to..." not "Moves cursor to...")
- Limit the first line to 72 characters or less
- Reference issues and pull requests liberally after the first line
- When only changing documentation, include `[ci skip]` in the commit title

### Kotlin Styleguide

All Kotlin code must adhere to the [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html):

- Use 4 spaces for indentation, not tabs
- Place opening braces on the same line
- Use camelCase for variable and function names
- Use PascalCase for class and interface names
- Use meaningful variable names
- Keep functions short and focused
- Add KDoc comments for public APIs

### Jetpack Compose Styleguide

- Follow [Compose API guidelines](https://developer.android.com/jetpack/compose/documentation)
- Use Modifier extension functions
- Prefer state hoisting
- Use @Composable naming conventions
- Add documentation for reusable composables

## Additional Notes

### Issue and Pull Request Labels

This section lists the labels we use to help us track and manage issues and pull requests.

#### Type of Issue and Issue State
- `bug` - Issues that are bugs
- `enhancement` - Issues that are feature requests
- `documentation` - Issues with documentation
- `beginner` - Good for newcomers
- `help wanted` - Extra attention is needed
- `question` - Further information is requested

#### Pull Request Labels
- `work in progress` - Pull requests which are still being worked on
- `needs review` - Pull requests which need code review
- `under review` - Pull requests being reviewed
- `requires changes` - Pull requests which need to be updated
- `ready to merge` - Pull requests which are approved and ready to merge

Thank you for reading and contributing to Mobile IDE!