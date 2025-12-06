# CSV Software Quality Analysis

[![CI/CD Pipeline](https://github.com/aalbakoush-design/CSV-SD/actions/workflows/ci.yml/badge.svg)](https://github.com/aalbakoush-design/CSV-SD/actions/workflows/ci.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=aalbakoush-design_CSV-SD&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=aalbakoush-design_CSV-SD)

A comprehensive Java library for robust CSV processing, demonstrating best practices in Software Quality Assurance, Testing, and DevOps.

## Features
- **Core Processing**: Read, Write, and Filter CSV files.
- **Validation**: Strict validation of headers and column counts.
- **Conversion**: Export to JSON and Map structures.
- **Statistics**: Generate insights and data summaries.
- **Merging**: Combine multiple CSV datasets.

## Quality Standards
This project adheres to strict quality gates:
- **Test Coverage**: > 60% (Jacoco)
- **Mutation Score**: > 60% (PiTest)
- **Performance**: Verified by JMH Microbenchmarks
- **Security**: Scanned by SonarQube, Snyk, and GitGuardian

## Usage

### Docker
```bash
docker run -it aqeelomar/csv-analyzer:latest
```

### Build Locally
```bash
mvn clean install
```
