# Lex Chatbot with Spring Boot and AWS Services

This project is a simple Lex Chatbot implemented using Spring Boot and integrated with AWS Comprehend and AWS Translator services. It allows users to place orders through a chat interface.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [Usage](#usage)
- [Deployment](#deployment)

## Prerequisites

Before you begin, ensure you have met the following requirements:

- Java Development Kit (JDK) 8 or later
- Maven for building the project
- AWS account with IAM credentials set up
- AWS Comprehend and AWS Translator services configured
- AWS Lex Bot created and configured

## Getting Started

1. Clone the repository:

```bash
git clone https://github.com/yourusername/lex-chatbot-spring-boot.git
cd lex-chatbot-spring-boot
```

2. Build the project using Maven:
  ```bash
  mvn clean install
  ```
3. Run the application:
   ```bash
   java -jar target/lex-chatbot-spring-boot-1.0.jar
   ```
## Configuration
Before running the application, you need to configure AWS credentials and the Lex Bot details. You can do this by modifying the application.properties file:
```.properties
# AWS credentials
aws.accessKeyId=YOUR_AWS_ACCESS_KEY
aws.secretKey=YOUR_AWS_SECRET_KEY

# AWS region
aws.region=us-east-1

# AWS Lex Bot configuration
lex.botName=YourLexBotName
lex.alias=YourBotAlias
```
Replace YOUR_AWS_ACCESS_KEY, YOUR_AWS_SECRET_KEY, YourLexBotName, and YourBotAlias with your AWS credentials and Lex Bot information.
## Usage
- Open a web browser and navigate to http://localhost:8080 to access the Lex Chatbot interface.

- Start a conversation with the chatbot by typing messages.

- The chatbot will use AWS Comprehend to analyze user input and AWS Translator to handle multilingual support.

- The chatbot can assist users in placing orders based on their messages.

## Deployment

You can deploy this Spring Boot application to a cloud platform of your choice, such as AWS Elastic Beanstalk or AWS Lambda, for production use. Make sure to update the AWS credentials and configuration accordingly in your deployment environment




