<a id="readme-top"></a>

<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/gayanukabulegoda/FieldGuardian-BACKEND">
    <img src="/src/main/resources/assets/FieldGuardian-logo-for-readme.png" alt="FieldGuardian Logo" width="90" height="90">
  </a>

<h2 align="center">FieldGuardian-BACKEND</h2>

  <p align="center">
   Welcome to the <strong>FieldGuardian</strong> backend repository! This backend service powers a comprehensive crop monitoring system for <strong>Green Shadow (Pvt) Ltd</strong>. The platform allows efficient management of fields, crops, staff, vehicles, and equipment, ensuring high-quality production and streamlined operations.
    <br />
    <a href="https://github.com/gayanukabulegoda/FieldGuardian-BACKEND/tree/main/src/main/java/lk/ijse/fieldguardianbackend"><strong>Explore the project »</strong></a>
    <br />
    <br />
    ·
    <a href="https://github.com/gayanukabulegoda/FieldGuardian-BACKEND/issues/new?labels=bug">Report Bug</a>
    ·
    <a href="https://github.com/gayanukabulegoda/FieldGuardian-BACKEND/issues/new?labels=enhancement">Request Feature</a>
    .
  </p>
</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li>
      <a href="#api-documentation">API Documentation</a>
    </li>
    <li>
      <a href="#frontend-repository">Frontend Repository</a>
    </li>
    <li><a href="#license">License</a></li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->

## About The Project

Green Shadow (Pvt) Ltd. is a mid-scale farm specializing in root crops and cereals. The company operates at both national and international levels and is renowned for high-quality production. Due to recent growth, the management has decided to introduce a comprehensive system to manage their crops and other assets. The proposed project will focus on systemizing the following areas:

- **Field**: Represents the land allocated for cultivation, with several fields designated for specific crop types.
- **Crop**: Refers to the type of crop grown in a particular field.
- **Staff**: Manages human resources as well as field and crop operations.
- **Monitoring Log**: Records observations and activities related to fields and crops.
- **Vehicle**: Manages vehicles assigned to staff for monitoring and supporting agricultural operations.
- **Equipment**: Oversees the agricultural equipment used in various operations.

### Business Process of the Proposed System

1. **User Access**: Users can log into the system as MANAGER, ADMINISTRATIVE, or SCIENTIST. No access is granted to other users; however, their data will be available in the respective tables.
2. **CRUD Operations**: Principal users can perform CRUD (Create, Read, Update, Delete) operations on the relevant entities.
3. **Crop Data**: Includes crop type, growth stage, total extent, and field observations.
4. **Non-Crop Data**: Covers human resources, equipment, and vehicles.
5. **Data Analysis**:
    - **Relational Analysis**: Enables evaluation of relationships, such as driver and labor allocations to vehicles.
    - **Spatial and Temporal Analysis**: Supports location-based and time-based analysis of resources.
6. **Permissions and Access Limitations**:
    - **MANAGER**: Full access to perform all CRUD operations.
    - **ADMINISTRATIVE**: Cannot edit crop data, field data, or monitor logs related to crop details.
    - **SCIENTIST**: Cannot modify staff, vehicle, or equipment data.

### Main Services of the System

- **Field Service**: Manages fields allocated for cultivation.
- **Crop Service**: Handles information related to crop types and growth stages.
- **Staff Service**: Manages human resources and their assignments.
- **MonitoringLog Service**: Records and tracks crop-related observations and activities.
- **Vehicle Service**: Oversees vehicle management and allocations for staff and operations.
- **Equipment Service**: Manages agricultural equipment used in various operations.
- **Auth Service**: Handles user authentication and access control.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Built With

This project was developed using a range of modern backend technologies and frameworks to ensure robust performance, scalability, and secure data handling.

[![SPRING](https://img.shields.io/badge/Spring_Framework-black?style=for-the-badge&logo=spring&logoColor=green)](https://spring.io/projects/spring-framework)
[![SPRING DATA JPA](https://img.shields.io/badge/Spring_Data_JPA-black?style=for-the-badge&logo=spring&logoColor=green)](https://spring.io/projects/spring-data-jpa)
[![HIBERNATE](https://img.shields.io/badge/Hibernate-black?style=for-the-badge&logo=Hibernate&logoColor=BBAE79)](https://hibernate.org/orm/)
[![MAVEN](https://img.shields.io/badge/Maven-black?style=for-the-badge&logo=apachemaven&logoColor=C77361)](https://maven.apache.org/download.cgi)
[![MySQL](https://img.shields.io/badge/Mysql-black?style=for-the-badge&logo=mysql&logoColor=08668E)](https://www.mysql.com/downloads/)
[![POSTMAN](https://img.shields.io/badge/Postman-black?style=for-the-badge&logo=Postman&logoColor=FF713D)](https://www.postman.com/downloads/)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- GETTING STARTED -->

## Getting Started

Follow these steps to set up the project locally and get it running on your machine. The instructions will guide you through the process of cloning the repository, installing dependencies, and configuring any required settings.

### Prerequisites

Before you begin, ensure you have the following installed on your system:

- **[Java 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)** or higher: Ensure you have JDK installed and configured on your system to compile and run the application.
- **[MySQL](https://dev.mysql.com/downloads/)**: Ensure you have MySQL installed and running locally or remotely.
- **[Maven](https://mvnrepository.com/)**: Used for dependency management and building the project.
- **[Postman](https://www.postman.com/downloads/)** or any API client to test the API (optional, but recommended).

### Installation

Follow these steps to install and set up the project on your local machine:

1. **Clone the repository**

   ```sh
   git clone https://github.com/gayanukabulegoda/FieldGuardian-BACKEND.git

2. **Change git remote URL to avoid accidental pushes to the base project**
   ```sh
   git remote set-url origin github_username/repo_name
   git remote -v # confirm the changes
   ```
<p align="right">(<a href="#readme-top">back to top</a>)</p>

## API Documentation
Refer to the [Postman API Documentation](https://documenter.getpostman.com/view/36681432/2sAYBaBAHq) for detailed API endpoints and usage instructions.

## Frontend Repository
Access the frontend repository on GitHub [here](https://github.com/gayanukabulegoda/FieldGuardian-FRONTEND).

## License
Distributed under the MIT License. See [License](LICENSE) for more information.
<p align="right">(<a href="#readme-top">back to top</a>)</p>

##

<div align="center">
<a href="https://github.com/gayanukabulegoda" target="_blank"><img src="https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white"></a>
<a href="https://git-scm.com/" target="_blank"><img src="https://img.shields.io/badge/Git-100000?style=for-the-badge&logo=git&logoColor=white"></a>
<a href="https://spring.io/projects/spring-boot" target="_blank"><img src = "https://img.shields.io/badge/Spring_Boot-100000?style=for-the-badge&logo=spring-boot&logoColor=white"></a>
<a href="https://spring.io/projects/spring-data-jpa" target="_blank"><img src = "https://img.shields.io/badge/Spring_Data_JPA-100000?style=for-the-badge&logo=spring&logoColor=white"></a>
<a href="https://spring.io/projects/spring-security" target="_blank"><img src = "https://img.shields.io/badge/Spring_Security-100000?style=for-the-badge&logo=Spring-Security&logoColor=white"></a>
<a href="https://hibernate.org/orm/releases/5.6/" target="_blank"><img src="https://img.shields.io/badge/Hibernate-100000?style=for-the-badge&logo=Hibernate&logoColor=white"></a>
<a href="https://jwt.io/" target="_blank"><img src = "https://img.shields.io/badge/JWT-100000?style=for-the-badge&logo=JSON%20web%20tokens&logoColor=white"></a>
<a href="https://www.json.org/json-en.html" target="_blank"><img src = "https://img.shields.io/badge/json-100000?style=for-the-badge&logo=json&logoColor=white"></a>
<a href="https://docs.oracle.com/javase/jndi/docs.html" target="blank"><img src = "https://img.shields.io/badge/JNDI-100000?style=for-the-badge&logo=oracle&logoColor=white"></a>
<a href="https://maven.apache.org/download.cgi" target="blank"><img src = "https://img.shields.io/badge/Maven-100000?style=for-the-badge&logo=apachemaven&logoColor=white"></a>
<a href="https://www.mysql.com/downloads/" target="blank"><img src = "https://img.shields.io/badge/Mysql-100000?style=for-the-badge&logo=mysql&logoColor=white"></a>
<a href="https://logback.qos.ch/documentation.html" target="_blank"><img src = "https://img.shields.io/badge/Logback-100000?style=for-the-badge&logo=ko-fi&logoColor=white"></a>
<a href="https://www.postman.com/downloads/" target="blank"><img src = "https://img.shields.io/badge/Postman-100000?style=for-the-badge&logo=Postman&logoColor=white"></a>
</div> <br>
<p align="center">
  &copy; 2024 Gayanuka Bulegoda
</p>