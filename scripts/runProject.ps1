$currentUser= $env:Username

New-Item -ItemType Directory -Force -Path C:\Users\$currentUser\Desktop\IvanSrdic

cd C:\Users\$currentUser\Desktop\IvanSrdicDocker

git clone https://github.com/isrdic/loan-calculator.git

cd loan-calculator\backend

mvn install

cd application\target

java -jar application-0.0.1.jar
