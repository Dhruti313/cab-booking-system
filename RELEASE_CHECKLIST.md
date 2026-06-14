# Release Checklist

Use this checklist before creating a production release.

## Pre-Release (Preparation Phase)

- [ ] **Code Review**: All changes reviewed and approved
- [ ] **Branch**: Working on or pulling from `main` branch
- [ ] **Documentation**: 
  - [ ] README.md is current
  - [ ] Architecture documentation updated
  - [ ] API documentation current (Swagger/OpenAPI)
  - [ ] CHANGELOG.md updated with release notes

- [ ] **Code Quality**:
  - [ ] All tests pass locally: `./mvnw clean verify`
  - [ ] Code coverage >80%
  - [ ] No critical or high-severity SonarQube issues
  - [ ] No dependency vulnerabilities
  - [ ] Linting issues resolved

- [ ] **Dependencies**:
  - [ ] Spring Boot version verified
  - [ ] GraphHopper and other core libraries current
  - [ ] Maven plugins up to date
  - [ ] No security vulnerabilities

## Release Creation

- [ ] **Version Number**: 
  - [ ] Semantic version chosen (MAJOR.MINOR.PATCH)
  - [ ] Follows project versioning scheme
  - [ ] Version updated in all pom.xml files

- [ ] **Git Operations**:
  - [ ] All local changes committed
  - [ ] No uncommitted modifications
  - [ ] Branch is up-to-date with remote
  - [ ] Git tag created: `git tag -a v1.x.x -m "Release message"`
  - [ ] Tag pushed: `git push origin v1.x.x`

- [ ] **Build Verification**:
  - [ ] All services build successfully
  - [ ] All tests pass
  - [ ] No build warnings or errors
  - [ ] Maven artifacts generated

- [ ] **Docker Images**:
  - [ ] Images build successfully
  - [ ] Images tagged correctly: `cab-booking/<service>:vX.X.X`
  - [ ] Image sizes reasonable
  - [ ] Images scanned for vulnerabilities

## Release Artifacts

- [ ] **JAR Files**:
  - [ ] rider-service-1.x.x.jar created
  - [ ] driver-service-1.x.x.jar created
  - [ ] trip-service-1.x.x.jar created

- [ ] **Release Notes**:
  - [ ] GitHub Release created
  - [ ] Release notes generated
  - [ ] Artifacts attached to release
  - [ ] Download links working

- [ ] **Deployment Files**:
  - [ ] Kubernetes manifests updated
  - [ ] Helm values updated
  - [ ] Docker Compose updated
  - [ ] Infrastructure configs current

## Testing & Validation

- [ ] **Unit Tests**: 100% pass rate
- [ ] **Integration Tests**: All passing
- [ ] **Smoke Tests**: All critical paths verified
- [ ] **Performance**: No performance regression
- [ ] **Security**: No security regressions

## Deployment Staging

- [ ] **Staging Environment**:
  - [ ] Build deployed to staging
  - [ ] Health checks passing
  - [ ] All services accessible
  - [ ] Database migrations successful

- [ ] **Smoke Testing on Staging**:
  - [ ] API endpoints responding
  - [ ] Database connectivity verified
  - [ ] Cache (Redis) working
  - [ ] Message broker (Kafka) connected
  - [ ] Monitoring/metrics collected

## Production Deployment

- [ ] **Pre-Deployment**:
  - [ ] Backup database
  - [ ] Backup current deployment configuration
  - [ ] Communication sent to team

- [ ] **Deployment**:
  - [ ] Image pulled from registry
  - [ ] Containers started successfully
  - [ ] Health checks passing
  - [ ] Services responding

- [ ] **Post-Deployment**:
  - [ ] Verify all services running
  - [ ] Check logs for errors
  - [ ] Verify metrics/monitoring
  - [ ] Test critical user flows
  - [ ] Monitor error rates

## Post-Release

- [ ] **Documentation**:
  - [ ] Release notes published
  - [ ] Changelog updated for next version
  - [ ] Known issues documented
  - [ ] Future roadmap communicated

- [ ] **Communication**:
  - [ ] Team notified of release
  - [ ] Release notes shared
  - [ ] Deployment verification confirmed

- [ ] **Monitoring**:
  - [ ] Error rates normal
  - [ ] Performance metrics good
  - [ ] No customer-reported issues
  - [ ] Infrastructure stable

## Rollback Plan (if needed)

- [ ] **Rollback Procedure Known**:
  - [ ] Previous version identified: `v1.x.x-1`
  - [ ] Rollback procedure tested
  - [ ] Runbooks available

- [ ] **Execute Rollback** (if critical issue):
  - [ ] Identify issue
  - [ ] Decide to rollback
  - [ ] Execute rollback
  - [ ] Verify stability
  - [ ] Post-incident review

## Sign-Off

- **Release Manager**: _________________ Date: _________
- **QA Lead**: _________________ Date: _________
- **Operations**: _________________ Date: _________
- **Product Owner**: _________________ Date: _________

## Notes

Use this section to document any special considerations or issues:

```
[Notes here]
```

---

**Release Version**: v__________
**Release Date**: __________
**Release Branch**: __________
**Git Tag**: v__________
