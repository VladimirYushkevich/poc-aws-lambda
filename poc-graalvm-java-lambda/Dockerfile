FROM arm64v8/amazonlinux:2

RUN yum -y update \
    && yum install -y tar zip unzip gzip bzip2-devel ed gcc gcc-c++ gcc-gfortran \
    less libcurl-devel openssl openssl-devel readline-devel xz-devel \
    zlib-devel glibc-static libcxx libcxx-devel llvm-toolset-7 zlib-static \
    && rm -rf /var/cache/yum

# Configure the build environment
ENV GRAAL_VERSION 22.3.3
ENV GRAAL_FOLDERNAME graalvm-ce-java11-${GRAAL_VERSION}
ENV GRAAL_FILENAME graalvm-ce-java11-linux-aarch64-${GRAAL_VERSION}.tar.gz
RUN curl -4 -L https://github.com/graalvm/graalvm-ce-builds/releases/download/vm-${GRAAL_VERSION}/${GRAAL_FILENAME} | tar -xvz
RUN mv $GRAAL_FOLDERNAME /usr/lib/graalvm
RUN rm -rf $GRAAL_FOLDERNAME

ENV MVN_VERSION 3.9.4
ENV MVN_FOLDERNAME apache-maven-${MVN_VERSION}
ENV MVN_FILENAME apache-maven-${MVN_VERSION}-bin.tar.gz
RUN curl -4 -L https://mirrors.ukfast.co.uk/sites/ftp.apache.org/maven/maven-3/${MVN_VERSION}/binaries/${MVN_FILENAME} | tar -xvz
RUN mv $MVN_FOLDERNAME /usr/lib/maven
RUN rm -rf $MVN_FOLDERNAME
RUN ln -s /usr/lib/maven/bin/mvn /usr/bin/mvn

#Native Image dependencies
RUN /usr/lib/graalvm/bin/gu install native-image
RUN ln -s /usr/lib/graalvm/bin/native-image /usr/bin/native-image

ENV JAVA_HOME /usr/lib/graalvm

WORKDIR /function

## Cache and copy dependencies
ADD pom.xml .
#ADD bootstrap .

# Compile the function
ADD . .
RUN mvn clean package -P native-image

# Copy the function artifact and dependencies onto a clean base
#FROM base
#WORKDIR /function

#COPY --from=build /src/target/dependency/*.jar /tmp
#ADD /src/target/*.jar ./
#ADD /src/target/poc-graalvm-java-lambda ./
#RUN chmod +x ./native

#RUN unzip aws-lambda-java-runtime-interface-client-2.4.1-linux-aarch_64.jar
#RUN unzip /tmp/aws-lambda-java-runtime-interface-client-2.4.1.jar -d out && cp out/jni/libaws-lambda-jni.*.so /function && rm -r out
RUN chmod +x bootstrap
RUN chmod +x ./target/poc-graalvm-java-lambda
RUN zip function.zip bootstrap ./target/poc-graalvm-java-lambda
RUN ls -lt /function

# Set runtime interface client as default command for the container runtime
#ENTRYPOINT [ "./poc-graalvm-java-lambda", "-Dcom.amazonaws.services.lambda.runtime.api.client.runtimeapi.NativeClient.JNI=/tmp/libaws-lambda-jni.linux-aarch_64.so", "-cp", "./*", "com.amazonaws.services.lambda.runtime.api.client.AWSLambda" ]
## Pass the name of the function handler as an argument to the runtime
#CMD [ "com.graalvm.Handler::handleRequest" ]
ENTRYPOINT ["sh"]