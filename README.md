# SchoolApp

A legacy **school management system** that I started building in **2015 while at university**. I continued developing it into my **fourth year in 2016**, when I formally presented the same system as my university project. The application was designed as a full Java web platform for managing day-to-day secondary-school operations, including students, staff, academics, fees, examinations, reporting, communication, and administrative workflows.

This repository preserves the original implementation and its history as an example of an early real-world Java enterprise application.

> **Historical project:** this codebase uses technologies, libraries, patterns, and security practices from its original era. It should be treated as a legacy system and reviewed carefully before any modern production use.

## What the system does

The application contains modules and workflows for areas such as:

- Student admission and registration
- Student biodata, parents, sponsors, houses, streams, and class placement
- Student search, update, deletion, and clearance
- Staff and teacher records
- Departments, duties, positions, class teachers, and house masters
- Classes and classrooms
- Subject management
- Examination setup and score processing
- CATs, end-term exams, papers, grading systems, performance, and deviations
- Excel-based student and examination imports
- Student fees and term fees
- Pocket-money deposits and withdrawals
- Other school charges and reverted transactions
- Student clearance and balance checks
- School account and configuration management
- SMS integration, including Africa's Talking-related components
- Internal chat/messaging components
- Student cards
- Book/student-book records
- Reports and printable documents
- Session and authentication utilities
- SOAP-based integration endpoints
- Administrative web interfaces

The codebase is relatively broad for a university-era project and represents an attempt to model a substantial portion of school administration in one system.

## Technology stack

| Area | Technology |
| --- | --- |
| Backend | Java |
| Web layer | Java Servlets, JSP |
| Frontend | HTML, CSS, JavaScript, AJAX |
| Database access | JDBC / DAO-style persistence |
| Build | Apache Ant |
| Packaging | WAR |
| Application server | WildFly |
| Database | Relational database / SQL-based persistence |
| Documents & reports | Java-based report/document generation |
| Spreadsheet processing | Excel import/export utilities |
| Integration | SOAP / WSDL |
| Messaging | SMS integration |
| Logging | Log4j |
| IDE metadata | Eclipse project files |

## Project structure

The main application lives under:

```text
School/webapp/
```

A simplified view:

```text
School/
└── webapp/
    ├── src/
    │   └── com/yahoo/petermwenda83/
    │       ├── bean/              # Domain models
    │       ├── server/            # Server-side logic
    │       │   ├── persistence/   # Database access
    │       │   ├── servlet/       # HTTP/application workflows
    │       │   ├── session/       # Session management
    │       │   └── util/          # Shared utilities
    │       └── util/              # Supporting utilities
    ├── web/
    │   ├── admin/                 # Administrative UI
    │   ├── school/                # School-facing UI
    │   ├── css/
    │   ├── js/
    │   ├── images/
    │   ├── resources/
    │   └── WEB-INF/
    ├── etc/                       # Runtime/configuration resources
    ├── docs/                      # Documentation / generated docs
    ├── build.xml                  # Ant build
    └── build.properties           # Build configuration
```

## Domain model

The application contains a sizeable set of domain objects. Examples include:

### Students

Student-related functionality includes:

- Basic student information
- Admission and registration
- Class/stream assignment
- Parent and sponsor information
- Student house assignment
- Student photos
- Student status and lifecycle
- Student cards
- Clearance
- Student balance lookup
- Excel upload/import

### Academics and examinations

The examination domain includes objects and workflows such as:

- Exam configuration
- CAT 1 / CAT 2
- End-term exams
- Paper 1 / Paper 2 / Paper 3
- Grading systems
- Student exam records
- Performance calculations
- Statistical/deviation-related processing
- Per-class Excel score uploads

### Finance

The finance-related domain includes:

- Student fees
- Term fees
- Pocket money
- Deposits
- Withdrawals
- Other school charges
- Reverted transactions
- Student clearance balances

### Staff and administration

Staff-related models include:

- Staff
- Staff details
- Departments
- Positions
- Duties
- Class teachers
- Teacher departments
- Teacher duties
- House masters

### Other modules

The repository also contains implementations for:

- School account configuration
- SMS API configuration and sending
- Africa's Talking integration components
- Internal chat
- Books and student-book records
- Session statistics
- Duplicate detection
- Background/thread management

## Architecture

The application follows a traditional server-rendered Java web architecture:

```text
Browser
   |
   v
JSP / HTML / CSS / JavaScript / AJAX
   |
   v
Java Servlets
   |
   v
Application / Domain Logic
   |
   v
DAO / Persistence Layer
   |
   v
Relational Database
```

Additional integration paths include:

```text
Application
   ├── Excel import/export
   ├── SOAP / WSDL services
   ├── SMS provider integration
   └── Printable/report output
```

This predates the modern Spring Boot + SPA style that is common today and reflects the architecture typically used in Java web systems of that period.

## Build and deployment

The project uses **Apache Ant**.

The Ant build contains targets for:

- cleaning build directories
- preparing the build structure
- compiling Java sources
- assembling the web application
- generating a WAR
- deploying the WAR to a WildFly deployment folder
- generating Javadocs

From:

```bash
cd School/webapp
```

the historical build is driven through:

```bash
ant
```

or specific targets such as:

```bash
ant clean
ant compile
ant makeWar
ant deployWar
ant javadoc
```

The exact build depends on values in `build.properties`, local JARs, runtime paths, and the original environment.

## Legacy environment note

This project was not created with today's dependency-management and deployment conventions.

Before trying to run it, expect to review:

- `build.properties`
- application-server paths
- database connection configuration
- local library/JAR locations
- environment-specific filesystem paths
- logging configuration
- SMS/API credentials
- deployment descriptors
- Java/JDK compatibility

Some of these assumptions are tied to the original development environment.

## Security note

Because this is historical code, **do not assume the authentication, credential storage, encryption, dependency versions, input handling, or configuration practices satisfy current security standards**.

Anyone modernizing the project should review at least:

- password hashing
- secrets management
- SQL injection protection
- session handling
- authorization
- dependency vulnerabilities
- file uploads
- XML/SOAP parsing
- logging of sensitive data
- external API credentials

## Historical context

I started building SchoolApp in **2015 while at university**. Development continued into **2016, my fourth year**, when I formally presented the same application as my university project.

It was not only an academic exercise. I developed it as a practical school-management product intended for real school operations, covering areas such as student records, fees, examinations, staff management, reporting, SMS communication, and integrations.

The project also moved beyond the university setting into real-world use:

| Year | Milestone |
| --- | --- |
| **2015** | Initial development started while I was at university |
| **2016** | Presented as my fourth-year university project |
| **2016** | Sold and deployed to the first school |
| **2017** | Sold to four additional schools |
| **Total** | Adopted by **5 schools** |

That progression, from a university project to software purchased by five schools, is an important part of the project's history. It gave me early experience not only in software development, but also in turning software into a usable product for real institutions, responding to operational needs, and supporting a system outside the classroom.

The repository is valuable to me as a record of my early software-engineering work and of how I approached a fairly large business domain before later moving into modern backend systems, APIs, integrations, messaging, and distributed architectures.

The source was originally hosted in Bitbucket and was later migrated to GitHub with its Git history preserved.

## Modernization ideas

A modern version of this application could retain the domain knowledge while replacing much of the underlying platform.

Possible modernization areas include:

- Java 21+ / current LTS Java
- Spring Boot or modern Jakarta EE
- Maven or Gradle
- RESTful APIs
- Angular or React frontend
- PostgreSQL with schema migrations
- JPA/Hibernate or another modern persistence layer
- OAuth2 / OpenID Connect
- modern password hashing
- role-based access control
- containerization with Docker
- Kubernetes deployment
- GitHub Actions CI/CD
- automated unit/integration tests
- dependency and security scanning
- structured logging and observability
- OpenAPI documentation
- event-driven integrations where appropriate

A modernization would ideally preserve the existing business concepts while separating the system into clearer modules and interfaces.

## Repository status

**Status:** Legacy / historical project

The code is preserved primarily for:

- historical reference
- learning
- architectural comparison
- portfolio context
- possible future modernization

It is **not currently maintained as a production-ready application**.

## Author

**Peter Mwenda — [msomi22](https://github.com/msomi22)**

Software Engineer focused on Java, backend systems, APIs, integrations, messaging, and distributed systems.

---

_Started in 2015, presented as my fourth-year university project in 2016, and subsequently sold to 5 schools. Migrated from Bitbucket to GitHub with repository history preserved._
