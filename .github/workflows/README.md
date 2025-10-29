# CI/CD Workflows Documentation

This directory contains GitHub Actions workflows for continuous integration, dependency management, and deployment.

## Workflows Overview

### 1. CI - Build, Test & Security (`ci.yml`)

**Triggers:**
- Push to `main` or `develop` branches
- Pull requests to `main` or `develop` branches
- Manual trigger via workflow_dispatch

**Jobs:**
- **build-and-test**: Builds the app and runs unit tests
  - Compiles the Android application
  - Executes unit tests
  - Generates test coverage reports (if configured)
  - Uploads test results as artifacts

- **instrumentation-tests**: Runs Android instrumentation tests
  - Uses Android Emulator (API 30)
  - Executes connected Android tests
  - Uploads test results as artifacts

- **security-scan**: Performs security analysis
  - Uses CodeQL for static code analysis
  - Scans for security vulnerabilities in Java/Kotlin code
  - Reports findings to GitHub Security tab

- **dependency-scan**: Checks for vulnerable dependencies
  - Runs OWASP Dependency Check (if configured)
  - Generates vulnerability reports

- **lint**: Runs Android Lint checks
  - Performs static code analysis
  - Generates lint reports

**Required Secrets:**
- `MAPS_API_KEY`: Google Maps API key (optional for CI, falls back to dummy key)

### 2. Dependency Update Check (`dependency-updates.yml`)

**Triggers:**
- Scheduled: Every Monday at 9:00 AM UTC
- Manual trigger via workflow_dispatch

**Jobs:**
- **check-kotlin-updates**: Monitors Kotlin version updates
  - Compares current version against latest GitHub release
  - Reports if updates are available

- **check-google-services-updates**: Monitors Google Services updates
  - Checks Google Maps SDK version
  - Provides links to official release notes

- **check-material-updates**: Monitors Material Components updates
  - Checks Compose BOM version
  - Checks Material Icons Extended version
  - Provides links to official documentation

- **gradle-dependency-updates**: Generates comprehensive dependency report
  - Uses Gradle's dependency update capabilities
  - Creates update reports as artifacts

- **create-update-summary**: Aggregates all update checks
  - Creates a summary of available updates
  - Provides recommendations for dependency management

**Recommendations:**
- Consider adding the [Gradle Versions Plugin](https://github.com/ben-manes/gradle-versions-plugin) for more comprehensive tracking
- Set up Dependabot or Renovate for automated dependency update PRs

### 3. Deployment & Release (`deployment.yml`)

**Triggers:**
- Push to version tags (e.g., `v1.0.0`, `v2.1.3`)
- Manual trigger via workflow_dispatch with release type selection

**Jobs:**
- **build-release**: Builds production-ready artifacts
  - Compiles release APK
  - Builds release AAB (Android App Bundle)
  - Uploads artifacts for distribution

- **create-github-release**: Creates GitHub release
  - Generates release with changelog
  - Attaches APK and AAB files
  - Marks as pre-release for alpha/beta builds

- **deploy-to-play-store**: Play Store deployment (TODO)
  - ⚠️ Not yet configured - contains TODO placeholders
  - Requires Play Console service account setup
  - Needs signing key configuration

- **deploy-to-app-store**: App Store deployment (TODO)
  - ⚠️ Not yet configured - requires iOS app implementation
  - Disabled until iOS support is added

- **notify-update-available**: Update notifications (TODO)
  - ⚠️ Not yet configured - contains TODO placeholders
  - WebSocket notification system not implemented
  - Website update API not implemented

- **deployment-summary**: Creates deployment summary
  - Reports completed deployment steps
  - Lists remaining TODO items

**Release Types:**
- `alpha`: Early testing release (pre-release)
- `beta`: Beta testing release (pre-release)
- `production`: Production release

**Required Secrets (for full deployment - currently TODOs):**
- `MAPS_API_KEY`: Google Maps API key
- `KEYSTORE_BASE64`: Base64-encoded signing keystore
- `KEYSTORE_PASSWORD`: Keystore password
- `KEY_PASSWORD`: Key password
- `KEY_ALIAS`: Key alias
- `PLAY_STORE_SERVICE_ACCOUNT_JSON`: Play Console service account JSON
- `WEBSOCKET_BACKEND_URL`: WebSocket server URL (future)
- `WEBSOCKET_API_KEY`: WebSocket API key (future)
- `WEBSITE_API_URL`: Website API URL (future)
- `WEBSITE_API_KEY`: Website API key (future)

## Setup Instructions

### Initial Setup

1. **Enable GitHub Actions:**
   - Go to repository Settings → Actions → General
   - Enable "Allow all actions and reusable workflows"

2. **Add Required Secrets:**
   - Go to repository Settings → Secrets and variables → Actions
   - Add `MAPS_API_KEY` with your Google Maps API key

3. **Configure Branch Protection (Optional):**
   - Set up branch protection rules for `main` branch
   - Require status checks to pass before merging

### For Deployment Setup (Future TODOs)

1. **Configure App Signing:**
   - Generate a release keystore
   - Add signing configuration to `app/build.gradle.kts`
   - Add signing secrets to GitHub repository

2. **Set Up Play Store Deployment:**
   - Create a service account in Google Play Console
   - Download the JSON key file
   - Add `PLAY_STORE_SERVICE_ACCOUNT_JSON` to GitHub secrets
   - Uncomment deployment steps in `deployment.yml`

3. **Set Up WebSocket Notifications:**
   - Implement backend WebSocket server
   - Create notification endpoint
   - Add WebSocket secrets to GitHub repository
   - Uncomment notification steps in `deployment.yml`

4. **Set Up Website Updates:**
   - Create website API endpoint for version updates
   - Add website API secrets to GitHub repository
   - Uncomment website update steps in `deployment.yml`

## Changelog Format

The deployment workflow expects changelog entries in the following format:

```markdown
## New Features
/--- [Feature Name] ---
- Feature description
- Another feature

## Bug Fixes
/ ----
- Bug fix description
- Another bug fix
```

Separators:
- Feature separator: `/--- [Feature Name] ---`
- General separator: `/ ----`

## Testing Workflows Locally

You can test build and test commands locally using Gradle:

```bash
# Build the app
./gradlew build

# Run unit tests
./gradlew test

# Run lint checks
./gradlew lintDebug

# Build release APK (requires signing configuration)
./gradlew assembleRelease

# Build release AAB
./gradlew bundleRelease
```

## Artifact Downloads

After workflow runs, you can download artifacts from the Actions tab:
- Test results
- Coverage reports
- Lint reports
- Dependency check reports
- Release APKs and AABs

## Monitoring

- **CI Status**: Check the Actions tab for build and test results
- **Security Alerts**: Check the Security tab for CodeQL findings
- **Dependency Updates**: Review weekly dependency update check summaries
- **Releases**: Check the Releases page for deployed versions

## Troubleshooting

### Build Failures
- Verify `MAPS_API_KEY` secret is set correctly
- Check Gradle wrapper is executable (`chmod +x gradlew`)
- Review build logs in Actions tab

### Test Failures
- Check test logs in uploaded artifacts
- Run tests locally to reproduce issues

### Security Scan Issues
- Review CodeQL alerts in Security tab
- Fix reported vulnerabilities in code
- Re-run workflow to verify fixes

### Deployment Issues
- Ensure all required secrets are configured
- Verify signing configuration is correct
- Check Play Console permissions for service account

## Future Enhancements

- [ ] Add automated changelog generation from commit messages
- [ ] Implement screenshot testing
- [ ] Add performance testing benchmarks
- [ ] Set up automated APK signing
- [ ] Configure Play Store deployment
- [ ] Implement WebSocket notification system
- [ ] Add website update integration
- [ ] Set up iOS deployment pipeline
- [ ] Add Slack/Discord notifications for releases
- [ ] Implement automated rollback on critical failures
