# File Processing Application
This project implements an application that reads data from a specified file path, with the path configurable as a parameter. The application continuously polls the directory for new files every 5 seconds. Upon finding a new file, the application processes it by summing all the numbers in the file.

### Functionality:
* File Processing: The application reads files from a configurable input directory and processes them.
* Output Generation: After processing each file, the application generates an output file containing the sum of all numbers present in the input file.
* File Movement: Upon successful processing, the input file is moved to a "processed" directory. If an error occurs during processing, the input file is moved to an "error" directory.

### Implementation
**Service Layer (FileServiceImpl)** <br/>
Dependencies: This service class depends on Spring's Value annotation to inject configurable parameters for input, output, processed, and error directories.
Processing Logic:
The processFiles method scans the input directory for files and processes each file.
For each file, it calculates the sum of numbers in the file using the sumFileContents method.
It then creates an output file containing the sum using the createOutputFile method.
Finally, it moves the processed file to the "processed" directory or the error file to the "error" directory based on the processing outcome. <br/>
**Integration Layer (FileIntegrationFlow)** <br/>
Configuration: This class configures Spring Integration for polling the input directory and handling incoming files.
Inbound Channel Adapter: It sets up a FileReadingMessageSource to continuously poll the input directory.
Message Handling: Incoming files are processed by invoking the processFiles method of the FileServiceImpl bean.

### Configuration
Ensure that the application.properties file contains the following configurations:
```
input.directory=C:/SITA_TEST_TASK/IN
output.directory=C:/SITA_TEST_TASK/OUT
processed.directory=C:/SITA_TEST_TASK/PROCESSED
error.directory=C:/SITA_TEST_TASK/ERROR
```
### Dependencies
* Spring Framework: Used for dependency injection and configuration.
* Spring Integration: Used for handling file integration and message processing.
* Java IO/NIO: Used for file handling operations like reading, writing, and moving files.
