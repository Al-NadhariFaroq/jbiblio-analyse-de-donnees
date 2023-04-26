FROM openjdk:17

WORKDIR app

# Copy necessary files
COPY target/devops-jbiblio-analyse-de-donnees-1.0-SNAPSHOT.jar .
COPY AUTHORS .
COPY Demo.java .
COPY launch.sh .

CMD ["bash", "launch.sh"]
