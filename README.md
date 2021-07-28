# Setup

Package with:
- `mvn clean package -DskipTests`

Then build the docker image:
- `docker build -t hendryang91/selenium-docker .`

Then start the container:
- `docker run --rm -it -v $PWD/test-output:/usr/share/udemy/test-output/ -e HUB_HOST=192.168.1.131 -e MODULE=search-module.xml hendryang91/seltest`
- OR `docker-compose up -d --scale chrome=4 --scale firefox=4`


check hub status:
- `curl -s localhost:4444/wd/hub/status`

`java -cp selenium-docker.jar:selenium-docker-tests.jar:libs/* -DBROWSER=firefox org.testng.TestNG ../search-module.xml`
- `-cp` is classpath. to specify all necessary jar/classes/libraries required to run the program
- `org.testng.TestNG` is the main class that requires .xml file as input args
- `../search-module.xml` is the test file to instruct TestNG main class