# Quick Start: Creating Your First Release

This guide walks you through creating your first release of the Cab Booking System.

## Prerequisites

- Git client installed and configured
- Docker installed
- All tests passing locally
- Working directory is clean (no uncommitted changes)

## Option 1: Using Git Tags (Simplest)

### Step 1: Update CHANGELOG.md

Edit [CHANGELOG.md](CHANGELOG.md) and add your release notes at the top:

```markdown
## [1.0.1] - 2026-06-15

### Added
- New feature description

### Fixed
- Bug fix description
```

### Step 2: Create Git Tag

```bash
# Navigate to project root
cd cab-booking-system

# Create annotated tag
git tag -a v1.0.1 -m "Release version 1.0.1: Bug fixes and improvements"

# Push tag to GitHub (this triggers the release workflow)
git push origin v1.0.1
```

### Step 3: Monitor Release

GitHub Actions will automatically:
1. Build all services
2. Run tests
3. Create Docker images
4. Generate release notes
5. Create GitHub Release

Check progress:
- Go to: https://github.com/Dhruti313/cab-booking-system/actions
- Watch the "Release" workflow

### Step 4: Verify Release

Once complete:
- Check GitHub Releases: https://github.com/Dhruti313/cab-booking-system/releases
- Verify Docker images were created
- Download artifacts if needed

---

## Option 2: Using Release Script (More Control)

### Step 1: Update CHANGELOG.md

Edit [CHANGELOG.md](CHANGELOG.md) with your release notes.

### Step 2: Run Release Script

```bash
# Navigate to project root
cd cab-booking-system

# Make script executable (first time only)
chmod +x scripts/release.sh

# Run release script
./scripts/release.sh 1.0.1
```

The script will:
- Validate version format (semantic versioning)
- Check you're on main branch
- Check for uncommitted changes
- Ask for confirmation
- Run all tests
- Update pom.xml files
- Build all services
- Create git tag
- Build Docker images

### Step 3: Push Tag

```bash
# After script completes, push the tag
git push origin v1.0.1
```

This triggers the GitHub Actions workflow.

### Step 4: Verify

Check GitHub Actions and Releases as above.

---

## Option 3: Using GitHub Actions UI (Easiest)

If you prefer not to use command line:

### Step 1: Update CHANGELOG.md

Update [CHANGELOG.md](CHANGELOG.md) in GitHub web UI:
1. Go to https://github.com/Dhruti313/cab-booking-system/blob/main/CHANGELOG.md
2. Click edit button
3. Add release notes at top
4. Commit changes

### Step 2: Trigger Release

1. Go to: https://github.com/Dhruti313/cab-booking-system/actions
2. Click "Release" workflow
3. Click "Run workflow" button
4. Enter version: `1.0.1`
5. Click "Run workflow"

### Step 3: Monitor

Watch the workflow run and complete.

### Step 4: Verify

Check Releases page for the completed release.

---

## After Release

### Update Version for Next Development

```bash
# Go to next development version (e.g., 1.0.2-SNAPSHOT)
cd rider-service
sed -i 's/<version>1.0.1<\/version>/<version>1.0.2-SNAPSHOT<\/version>/g' pom.xml

# Commit this change
git add .
git commit -m "chore: prepare for next development version"
git push origin main
```

### Deploy Released Version

#### Local Testing
```bash
# Run released version locally
./scripts/quick-start-release.sh v1.0.1
```

#### Kubernetes Deployment
```bash
# Update rider-service deployment
kubectl set image deployment/rider-service \
  rider-service=cab-booking/rider-service:v1.0.1

# Update driver-service deployment
kubectl set image deployment/driver-service \
  driver-service=cab-booking/driver-service:v1.0.1

# Update trip-service deployment
kubectl set image deployment/trip-service \
  trip-service=cab-booking/trip-service:v1.0.1

# Verify deployment
kubectl rollout status deployment/rider-service
```

#### Helm Deployment
```bash
helm upgrade cab-booking-system ./infrastructure/helm \
  --set image.tag=v1.0.1 \
  --namespace production
```

### Share Release Notes

1. Go to release on GitHub: https://github.com/Dhruti313/cab-booking-system/releases/tag/v1.0.1
2. Copy the release notes
3. Share in:
   - Team Slack/Chat
   - Email to stakeholders
   - Internal documentation wiki

---

## Common Issues & Solutions

### Issue: "Tag already exists"
```bash
# Delete the tag and recreate
git tag -d v1.0.1
git push origin --delete v1.0.1
git tag -a v1.0.1 -m "Release message"
git push origin v1.0.1
```

### Issue: "Uncommitted changes"
```bash
# Commit your changes first
git add .
git commit -m "commit message"
git push origin main

# Then create release
```

### Issue: "Tests failed in CI"
```bash
# Run tests locally first
./mvnw clean verify -pl rider-service

# Fix any failing tests
# Commit fixes
# Try release again
```

### Issue: "Docker images not building"
```bash
# Check Docker is running
docker ps

# Build manually
docker build -t cab-booking/rider-service:v1.0.1 rider-service/

# Verify image exists
docker images | grep rider-service
```

---

## Rollback if Something Goes Wrong

### Quick Rollback to Previous Version
```bash
# Find previous release
git tag | sort -V | tail -5

# Deploy previous version
kubectl set image deployment/rider-service \
  rider-service=cab-booking/rider-service:v1.0.0

# Or with Helm
helm rollback cab-booking-system 1 -n production
```

### Delete Release (if needed)
```bash
# Delete tag locally
git tag -d v1.0.1

# Delete on GitHub
git push origin --delete v1.0.1

# Delete GitHub Release via web UI
# Go to https://github.com/Dhruti313/cab-booking-system/releases
# Click "..." → Delete
```

---

## Checklist Before Each Release

- [ ] CHANGELOG.md updated
- [ ] All tests passing locally: `./mvnw clean verify`
- [ ] Code review completed
- [ ] No uncommitted changes
- [ ] On `main` branch
- [ ] Documentation is current

## What Gets Released

Each release includes:

✅ **Artifacts**
- JAR files for each service
- Docker images for each service
- Kubernetes manifests

✅ **Documentation**
- Release notes on GitHub
- CHANGELOG.md entry
- Git tag with message

✅ **Version Updates**
- pom.xml files updated
- VERSION file updated
- Docker image tags

---

## Next Release Checklist

For your next release (e.g., 1.1.0):

```markdown
## [1.1.0] - [DATE]

### Added
- [ ] Feature 1 description
- [ ] Feature 2 description

### Changed
- [ ] Improvement 1
- [ ] Improvement 2

### Fixed
- [ ] Bug 1
- [ ] Bug 2

### Dependencies
- [ ] Spring Boot: X.X.X
- [ ] Other library: X.X.X
```

---

## Still Have Questions?

📖 Read the full documentation:
- [RELEASE.md](RELEASE.md) - Complete release guide
- [RELEASE_CHECKLIST.md](RELEASE_CHECKLIST.md) - Pre-release checklist
- [CHANGELOG.md](CHANGELOG.md) - Release history
- [RELEASE_NOTES_TEMPLATE.md](RELEASE_NOTES_TEMPLATE.md) - Release notes template

🔧 Review the workflows:
- [.github/workflows/release.yml](.github/workflows/release.yml) - Release automation

---

**You're all set! Create your first release now!** 🚀
