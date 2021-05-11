## loan-calculator

####RUN:

- **runProject.ps1**
- **runProjectDocker.ps1**

You will need:
- git
- maven
- java
- docker insalled and running (for runProjectDocker.ps1 script)

#####Steps:
- Download runProjectDocker.ps1 script from repository https://github.com/isrdic/loan-calculator/tree/main/scripts to any location on your file system 
- Run Windows PowerShell
  - Go to location where you have downloaded runProjectDocker.ps1 (Example C:\Users\HP\IdeaProjects\loan-calculator\scripts>)
  - Run command .\RunProject.ps1

Script will clone repo on Desktop in folder IvanSrdic, 
will install it, run tests
and build and run docker image

After this App will be up and running and you can ping it on https://localhost:8090/ping