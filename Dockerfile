#Application type - downloads the image 
FROM openjdk:8-jdk-alpine

#Add jar to docker container
ADD target/SpringConversionFactorFeignClient-0.0.2-SNAPSHOT.jar springconversionfactorfeignclient.jar

#Expose the container on a port
EXPOSE 8071

#Command whith which docker container will run 
ENTRYPOINT ["java","-jar","springconversionfactorfeignclient.jar"]