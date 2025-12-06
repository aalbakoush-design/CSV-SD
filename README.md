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

### Docker / Executable JAR
The application is now a CLI tool. You can run it via Docker or directly with Java.

**General syntax:**
```bash
java -jar app.jar <command> <input-file> [arguments]
```

**Commands:**
- `filter <input> <column> <value> <output>`: Filter rows.
- `stats <input> [column]`: Show count or unique values.
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

### Docker / Executable JAR
The application is now a CLI tool. You can run it via Docker or directly with Java.

**General syntax:**
```bash
java -jar app.jar <command> <input-file> [arguments]
```

**Commands:**
- `filter <input> <column> <value> <output>`: Filter rows.
- `stats <input> [column]`: Show count or unique values.
- `convert <input>`: Convert to JSON.
- `validate <input> <header1,header2...>`: Validate headers.

**Example (Docker):**
```bash
# Must mount the file to make it accessible inside the container
docker run -v $(pwd):/data aqeelomar/csv-analyzer:latest stats /data/my-file.csv
```

### Build Locally
```bash
mvn clean package
java -jar target/csv-analyzer-1.0-SNAPSHOT.jar stats input.csv
```
