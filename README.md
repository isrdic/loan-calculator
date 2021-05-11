## loan-calculator

#### RUN:

- **runProjectDocker.ps1**
- **runProject.ps1**

You will need:
- git
- maven
- java
- docker insalled and running (for runProjectDocker.ps1 script)

##### Steps:

##### runProjectDocker.ps1
- Download runProjectDocker.ps1 script from repository https://github.com/isrdic/loan-calculator/tree/main/scripts to any location on your file system 
- Run Windows PowerShell
  - Go to location where you have downloaded runProjectDocker.ps1 (Example C:\Users\HP\IdeaProjects\loan-calculator\scripts>)
  - Run command .\runProjectDocker.ps1

Script will clone repo on Desktop in folder IvanSrdicDocker, 
will install project, run tests and build and run docker image

After this App will be up and running pingable on https://localhost:8090/ping.

Postman Collection for testing APIs is located i postman-collection folder of the project root.

##### runProjectDocker.ps1

- Download runProject.ps1 script from repository https://github.com/isrdic/loan-calculator/tree/main/scripts to any location on your file system
- Run Windows PowerShell
  - Go to location where you have downloaded runProject.ps1 (Example C:\Users\HP\IdeaProjects\loan-calculator\scripts>)
  - Run command .\runProject.ps1

Script will clone repo on Desktop in folder IvanSrdic,
will install project, run tests and jar file.

After this App will be up and running pingable on https://localhost:8090/ping.

Postman Collection for testing APIs is located i postman-collection folder of the project root.