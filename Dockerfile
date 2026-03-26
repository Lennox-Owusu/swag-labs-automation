# ── Stage 1: Build and test ───────────────────────────────────────
FROM maven:3.9.6-eclipse-temurin-21 AS test

# Install Chrome
RUN apt-get update && apt-get install -y \
    wget \
    gnupg \
    unzip \
    curl \
    fonts-liberation \
    libasound2 \
    libatk-bridge2.0-0 \
    libatk1.0-0 \
    libcups2 \
    libdbus-1-3 \
    libgdk-pixbuf2.0-0 \
    libnspr4 \
    libnss3 \
    libx11-xcb1 \
    libxcomposite1 \
    libxdamage1 \
    libxrandr2 \
    xdg-utils \
    --no-install-recommends \
    && wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | apt-key add - \
    && echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" \
       > /etc/apt/sources.list.d/google-chrome.list \
    && apt-get update \
    && apt-get install -y google-chrome-stable --no-install-recommends \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src

# Run tests
RUN mvn clean test \
    -Dheadless=true \
    -Dbrowser=chrome \
    -B \
    || true

# Generate Allure report using Maven plugin (same as local)
RUN mvn allure:report -B

# The report is now at target/site/allure-maven-plugin
RUN ls -la /app/target/site/allure-maven-plugin

# ── Stage 2: Serve the Allure report ─────────────────────────────
FROM nginx:alpine AS report

RUN rm /usr/share/nginx/html/index.html

# Copy from the Maven plugin output directory
COPY --from=test /app/target/site/allure-maven-plugin /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]