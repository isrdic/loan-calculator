$currentUser= $env:Username

New-Item -ItemType Directory -Force -Path C:\Users\$currentUser\Desktop\IvanSrdic

cd C:\Users\$currentUser\Desktop\IvanSrdic

git clone https://github.com/isrdic/loan-calculator.git

cd loan-calculator\backend

mvn install

docker build -t loan-calculator .

docker run -p 8090:8090 loan-calculator