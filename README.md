# springboot-demo

A simple Spring Boot application that integrates with the OpenAI API, logs responses to AWS DynamoDB, and is deployable to AWS Elastic Beanstalk. Includes support for React frontend integration and environment-specific configuration.

---

## 📦 Features

- ✅ `/greeting` REST endpoint returns OpenAI-generated message
- ✅ Stores OpenAI responses in DynamoDB (`chatgpt_response_log`)
- ✅ Fetches historical responses by date
- ✅ Reads secrets from environment variables
- ✅ Deployed to Elastic Beanstalk (Java 17 platform)

---

## 🛠 Local Development

### ✅ Prerequisites

- Java 17
- Gradle (or use `./gradlew`)
- Node.js (if using frontend)
- AWS credentials (set as environment variables)
- OpenAI API key

### ✅ Environment Variables

Set these locally or in your IDE:

```bash
export AWS_ACCESS_KEY_ID=your-access-key
export AWS_SECRET_ACCESS_KEY=your-secret-key
export AWS_REGION=us-east-1
export OPENAI_KEY=sk-...
```

### ✅ Build and Run

```bash
./gradlew clean build
java -jar build/libs/springboot-demo-0.0.1-SNAPSHOT.jar
```

Visit: `http://localhost:8080/greeting`

---

## 🚀 Deploying to Elastic Beanstalk

### ✅ Setup (One Time)

```bash
eb init
eb create springboot-env
eb setenv AWS_ACCESS_KEY_ID=... AWS_SECRET_ACCESS_KEY=... AWS_REGION=us-east-1 OPENAI_KEY=...
```

### ✅ Deploy

```bash
./gradlew clean build
eb deploy
```

Visit: `eb open`

### ✅ Required Files

Ensure these are present in the project root:
- `springboot-demo-0.0.1-SNAPSHOT.jar`
- `Procfile` (contents: `web: java -jar springboot-demo-0.0.1-SNAPSHOT.jar`)

---

## 🗃 DynamoDB Schema

**Table:** `chatgpt_response_log`

| Attribute       | Type    | Key         |
|----------------|---------|-------------|
| `responseId`    | Number  | Partition   |
| `responseDate`  | String  | Sort        |
| `responseText`  | String  | -           |

---

## 🌐 Planned/Optional Features

- [ ] UI frontend integration (React)
- [ ] Custom prompts from UI
- [ ] OpenAI model selector
- [ ] Error handling & validation
- [ ] AWS S3 or CloudFront for frontend hosting

---

## 🧪 Test Endpoints

- `GET /greeting` — returns OpenAI message + recent logs
- Environment variables must be configured before use

---

## 📁 Folder Structure

```bash
springboot-demo/
├── src/
├── build.gradle
├── Procfile
├── springboot-demo-0.0.1-SNAPSHOT.jar
├── frontend/                # (optional)
└── deploy/                  # used for EB deployment
```

---

## 👥 Contributors

- Sam Collins – Initial developer & architect

---

## 📜 License

[MIT](LICENSE)