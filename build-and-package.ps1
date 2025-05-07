# PowerShell script to build and package a Spring Boot app for Elastic Beanstalk

# Define variables
$jarName = "springboot-demo-0.0.1-SNAPSHOT.jar"
$jarPath = ".\build\libs\$jarName"
$procfilePath = ".\Procfile"
$deployDir = ".\deploy"
$zipPath = ".\app.zip"

Write-Host "`n🔨 Building Spring Boot application..."
.\gradlew clean build

if (-Not (Test-Path $jarPath)) {
    Write-Error "❌ JAR file not found at $jarPath. Build failed?"
    exit 1
}

if (-Not (Test-Path $procfilePath)) {
    Write-Error "❌ Procfile not found. Please ensure 'Procfile' (not ProcFile.txt) exists in the root."
    exit 1
}

Write-Host "`n📦 Preparing deployment folder..."

# Clean and recreate deploy dir
Remove-Item $deployDir -Recurse -Force -ErrorAction SilentlyContinue
New-Item -ItemType Directory -Path $deployDir | Out-Null

# Copy necessary files
Copy-Item $jarPath "$deployDir\$jarName"
Copy-Item $procfilePath "$deployDir\Procfile"

# Create zip
if (Test-Path $zipPath) {
    Remove-Item $zipPath -Force
}

Write-Host "`n📁 Creating app.zip for deployment..."
Compress-Archive -Path "$deployDir\*" -DestinationPath $zipPath

if (-Not (Test-Path $zipPath)) {
    Write-Error "❌ Failed to create app.zip"
    exit 1
}

Write-Host "`n✅ app.zip created successfully at $zipPath"
Write-Host "👉 Upload this file via the AWS Elastic Beanstalk Console under your environment: 'Upload and Deploy'"
Write-Host "🔗 https://console.aws.amazon.com/elasticbeanstalk/"

# Optional: open the console in default browser
Start-Process "https://console.aws.amazon.com/elasticbeanstalk/"
