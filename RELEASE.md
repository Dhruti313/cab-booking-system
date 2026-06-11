# Release Guide

This document describes how to create and manage releases for the Cab Booking System project.

## Release Strategy

We follow **Semantic Versioning** (MAJOR.MINOR.PATCH) and **Conventional Commits** convention:
- **MAJOR**: Incompatible API changes or major architectural changes
- **MINOR**: Backwards-compatible new functionality
- **PATCH**: Backwards-compatible bug fixes

## Release Process

### Automated Release via Tags

The easiest way to create a release is to push a version tag:

```bash
# 1. Create and push a version tag
git tag -a v1.0.0 -m "Release version 1.0.0"
git push origin v1.0.0

# The GitHub Actions workflow will automatically:
# - Build all services
# - Run tests
# - Create Docker images
# - Generate release notes
# - Create a GitHub Release
```

### Manual Release via Workflow Dispatch

Alternatively, you can trigger a release manually:

1. Go to **Actions** → **Release** workflow
2. Click **Run workflow**
3. Enter the version number (e.g., `1.0.0`)
4. Click **Run workflow**

The workflow will:
- Update version numbers in all `pom.xml` files
- Build and test all services
- Create Docker images
- Create and push a Git tag
- Generate release notes

## Pre-Release Checklist

Before creating a release, ensure:

- [ ] All tests pass: `./mvnw clean verify`
- [ ] Code coverage is acceptable (>80%)
- [ ] No open critical or high-severity bugs
- [ ] Documentation is up to date
- [ ] `CHANGELOG.md` is updated with release notes
- [ ] All commits follow Conventional Commits convention
- [ ] Code review approved by at least one maintainer

## Version Numbering

### Current Version
Current: **1.0.0** (Production Ready)

### Version Files
Update version in these locations:
- `rider-service/pom.xml` (main service)
- `driver-service/pom.xml`
- `trip-service/pom.xml`
- Git tags: `v1.0.0`

## Docker Image Naming

Released Docker images follow this naming convention:

```
cab-booking/<service>:v<VERSION>
```

Examples:
- `cab-booking/rider-service:v1.0.0`
- `cab-booking/driver-service:v1.0.0`
- `cab-booking/trip-service:v1.0.0`

## Release Notes

Release notes are automatically generated from:
1. **Git commits** between releases (using conventional commits)
2. **Manual CHANGELOG.md** entries for detailed descriptions
3. **GitHub Release** template

### Updating CHANGELOG.md

When creating a release, update `CHANGELOG.md` following this template:

```markdown
## [1.1.0] - 2026-07-01

### Added
- New feature description
- Another feature

### Changed
- What changed from previous version
- Breaking changes if any

### Fixed
- Bug fix description
- Performance improvement

### Security
- Security fix if applicable
- Vulnerability patches
```

## Deployment

### Local Testing

Before releasing, test locally:

```bash
# Build services
./mvnw clean install

# Test with Docker Compose
docker-compose -f infrastructure/docker/docker-compose.yml up

# Run integration tests
./mvnw verify
```

### Production Deployment

After release, deploy using:

```bash
# Using Kubernetes
kubectl set image deployment/rider-service \
  rider-service=cab-booking/rider-service:v1.0.0

# Or using Helm
helm upgrade cab-booking-system ./infrastructure/helm \
  --set image.tag=v1.0.0
```

## Rollback Procedures

If a release has critical issues:

```bash
# 1. Identify the previous stable release
git tag | grep "^v" | sort -V | tail -5

# 2. Checkout previous version
git checkout v0.9.0

# 3. Deploy previous version
helm rollback cab-booking-system 1

# 4. Create a new patch release (e.g., v1.0.1) after fixing the issue
```

## Semantic Version Increments

### Patch Release (e.g., 1.0.0 → 1.0.1)
```bash
git tag -a v1.0.1 -m "Patch release: bug fixes"
git push origin v1.0.1
```

### Minor Release (e.g., 1.0.0 → 1.1.0)
```bash
git tag -a v1.1.0 -m "Minor release: new features"
git push origin v1.1.0
```

### Major Release (e.g., 1.0.0 → 2.0.0)
```bash
git tag -a v2.0.0 -m "Major release: breaking changes"
git push origin v2.0.0
```

## Monitoring Releases

### Check Release Status
```bash
# List all releases
git tag | sort -V

# View specific release
git show v1.0.0

# View release notes in GitHub
open https://github.com/Dhruti313/cab-booking-system/releases/tag/v1.0.0
```

### Verify Docker Images
```bash
# List available tags
docker image ls cab-booking/*

# Test image
docker run --rm cab-booking/rider-service:v1.0.0 --version
```

## Continuous Deployment

For continuous deployment, you can modify the release workflow to automatically:
1. Push images to container registry
2. Update Kubernetes deployments
3. Notify team via Slack/email
4. Update documentation sites

See [.github/workflows/release.yml](.github/workflows/release.yml) for current automation.

## Troubleshooting

### Release Build Failed
1. Check workflow logs in GitHub Actions
2. Run local build: `./mvnw clean verify`
3. Fix any issues and retry release

### Docker Image Not Found
1. Verify image was created: `docker image ls`
2. Check Docker daemon is running
3. Rebuild image: `docker build -t cab-booking/rider-service:v1.0.0 rider-service/`

### Cannot Push Tag
```bash
# If tag already exists
git tag -d v1.0.0  # Delete local
git push origin --delete v1.0.0  # Delete remote
git tag -a v1.0.0 -m "Release message"  # Recreate
git push origin v1.0.0  # Push
```

## Additional Resources

- [Keep a Changelog](https://keepachangelog.com/)
- [Semantic Versioning](https://semver.org/)
- [Conventional Commits](https://www.conventionalcommits.org/)
- [GitHub Actions Documentation](https://docs.github.com/en/actions)
