# UE03 — Integrate Docker into CI/CD (24 pts)

This exercise builds on **UE01 (Git/PR workflow)** and **UE02 (CI with build/tests, matrix, JaCoCo, SonarCloud)**. You will add **Docker** locally and in CI, publish the image as a **GitHub Actions artifact**, then download and run it locally. Submit a **PDF** (screenshots + short comments) and your **GitHub repo link**.

## Learning objectives
- Containerize the existing Java project with a multi-stage Dockerfile.
- Run the container **locally** and verify behavior.
- Integrate Docker into the existing pipeline (after build/tests/matrix/SonarCloud).
- Export the image as a **.tar artifact** named with the **short SHA**, download it, and run it locally.
- Practice clear naming, tagging, and reproducibility.

## Deliverables
1. **PDF** with concise screenshots and short comments:
    - Local Docker build & run
    - CI run (build summary, artifact list)
    - Download & local run of the CI-built image
    - Notes on issues & fixes (troubleshooting)

2. **GitHub repository link** (default branch up to date; a sample PR is welcome)

Submit your results to eLearning until **December 4th, 2026, 23:55**

## Prerequisites
- UE01 repository with Git/branching/PR basics
- UE02 CI already set up (build + unit tests with Surefire, matrix builds, JaCoCo, SonarCloud)
- Docker Desktop / Docker Engine installed
- The intentionally failing test from UE01 (`TextUtilsTest.isPalindrome_caseInsensitive_expectedTrue`) must be fixed (UE02, task 6). The build stage of your Dockerfile runs `mvn package` including the tests, so the Docker build fails as long as this test is red.

## Tasks & Points
| Section                    | Task                                                   | What we look for                                                                    |    Pts |
| -------------------------- | ------------------------------------------------------ | ----------------------------------------------------------------------------------- | -----: |
| **A. Local Containerization** | Multi-stage **Dockerfile** and **.dockerignore**       | Build stage (Maven) → runtime stage (Temurin JRE); reasonable ignore rules          |      4 |
|                            | Local **build** of the image                           | `docker build -t <owner>/<app>:dev .` succeeds                                      |      2 |
|                            | Local **run** of the container                         | Container outputs expected result                     |      2 |
|                            | Short analysis in PDF                                  | Images/Documentation + 1–2 optimization ideas (e.g., .dockerignore, non-root, base image)     |      1 |
| **B. CI Integration**      | Separate **Docker job** with sensible triggers         | Placed logically after tests/Sonar; path filters & concurrency avoid duplicate runs |      3 |
|                            | **Build** image and **tag** with short SHA | Single-arch build (for `load`) or justified alternative; clear tag naming           |      2 |
|                            | **Upload artifact** as `.tar` named with SHA           | Clear artifact name (e.g., artifact `docker-image-<sha>` containing `image-<sha>.tar`)                               |      2 |
|                            | **Clean integration** with existing pipeline           | Job dependencies (`needs`) correct, no Sonar double-analysis, minimal noise         |      2 |
|                            | **Readability & maintainability**                      | Good job/step names, consistent artifact/tag naming                                 |      2 |
| **C. Consume Artifact**    | Download artifact from Actions & load image locally                   | report shown in PDF, `docker load -i image-<sha>.tar`, tag visible in `docker images`                                                |      2 |
|                            |  Run image locally                                      | Container starts successfully; brief observation in PDF                             |      2 |
|                            | **Total**                                              |                                                                                     | **24** |





## Expected implementation guidance

### 1) Local Docker
- Multi-stage Dockerfile (build → runtime). Copy the built JAR to `/app/app.jar` 
- Keep `Dockerfile` at repo root; add a lean `.dockerignore` (e.g., `target/`, `.git/`, IDE/OS files).
- Verify locally: `docker build …` then `docker run …` and confirm the output. The app is a console program (no web server, no port). Expected output:
  ```text
  Sum(2,3) = 5
  sumUp=10, addAll=10
  isPalindrome('Anna')? true
  safeParseInt('42'): 42
  safeParseInt('x'): 0
  ```

### 2) CI Integration guidance
- Add a **separate job** for Docker that runs **after** your existing build/tests/matrix/Sonar job(s). Use `needs:` to express dependency.
- Use **Buildx;** for artifact export, prefer **single-arch** + `load: true`, then `docker save` the image and **upload as artifact**. Name artifacts with the **short SHA**.
- Use **path filters** (only rebuild Docker when relevant files change) and **concurrency** (avoid duplicate runs on rapid pushes).
- **Check your path filter from UE02.** If it only lists `pom.xml` and `src/**`, a push that changes only the `Dockerfile`, `.dockerignore` or the workflow file starts **no run at all**. Add these paths, e.g. `paths: [ 'pom.xml', 'src/**', 'Dockerfile', '.dockerignore', '.github/workflows/**' ]`.
- Short SHA in a step: `echo "short_sha=${GITHUB_SHA::7}" >> $GITHUB_OUTPUT` (give the step an `id` and read it with `steps.<id>.outputs.short_sha`).

### 3) Download & Run the CI Image
- Download the `docker-image-<sha>` artifact from the run.
- GitHub delivers the artifact as a **ZIP file**. Unzip it first, then load the `.tar` inside: `docker load -i image-<sha>.tar`. Confirm the tag via `docker images`.
- The CI image is built for `linux/amd64`. On an Apple Silicon Mac `docker run` prints a platform warning (`linux/amd64` does not match `linux/arm64`); the container still runs through emulation. Mention this in your PDF if you see it.
- Run locally and add a screenshot/snippet of the output.

### Acceptance criteria (checklist)
- **Dockerfile** is multi-stage; `.dockerignore` reduces context.
- **CI**: Docker job exists, is placed after tests/Sonar, and creates a **.tar artifact** named with short SHA.
- **SonarCloud** behaves as in UE02 (no duplicate Automatic Analysis vs. CI scan).
- **Reproducibility**: image tag and artifact names are traceable to the commit.
- **Documentation quality**: clear screenshots, short explanations, notes on issues and fixes.

### Hints
- Buildx cache can speed things up:
    - `cache-from: type=gha` / `cache-to: type=gha,mode=max`
- Add a quick diagnostic in the build stage once: `&& ls -la target` (to verify the JAR exists).
- If you see `no main manifest attribute`, either set a `Main-Class` (maven-jar-plugin) or run via classpath and FQCN (Fully Qualified Class Name). In this project the main class is `com.example.cicd.App` and the JAR is `target/ci-cd-uebung-1.0.0.jar`, e.g. `java -cp /app/app.jar com.example.cicd.App`. The lecture demo repo uses a different package (`com.example.hello`), so do not copy its `ENTRYPOINT` unchanged.
- Use current major versions of the actions: `docker/setup-buildx-action@v4`, `docker/build-push-action@v7`, `actions/upload-artifact@v7` (plus `actions/checkout@v7`, `actions/setup-java@v6` from UE02).
- Keep Docker concerns **separate** from unit tests/matrix/Sonar — Docker job comes **after** those checks.


### What to submit (recap)
- **PDF**: screenshots + brief comments showing local build/run, CI run, artifact download, local load & run, and any issues you solved.
- **Repository URL** with the updated workflow and Dockerfile in the default branch.