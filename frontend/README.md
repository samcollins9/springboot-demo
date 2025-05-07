# springboot-demo Frontend (React)

This is the React-based frontend for the `springboot-demo` project. Currently under development and not yet integrated into the deployed Elastic Beanstalk environment.

---

## 🚧 Status

- [x] Local development working
- [ ] Deployment integration with backend
- [ ] Dynamic prompt support
- [ ] Styling and error handling

---

## 🛠 Local Development

### ✅ Prerequisites

- Node.js >= 16
- npm or yarn

### 🚀 Start Dev Server

```bash
cd frontend
npm install
npm start
```

Visit: `http://localhost:3000`

---

## 🌐 Planned Features

- React form for sending prompts to backend
- Display OpenAI response history from DynamoDB
- API integration with Spring Boot `/greeting` and log endpoints
- Eventually served either via S3 or embedded in Spring Boot static resources

---

## 📁 Folder Structure

```bash
frontend/
├── public/
├── src/
├── package.json
└── README.md
```

---

## 👥 Contributors

- Sam Collins – UI and integration lead

---

## 📜 License

[MIT](LICENSE)