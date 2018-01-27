# JSON Validator

# Task 1
1) `docker build -t JSONvalidator github.com/AlNadraga/TRPO`
2) `docker run -d --rm -p 80:80 JSONvalidator`
3) `curl -s --upload-file filename.json http://localhost`

# Task 2
1) `./gradlew docker`
2) `docker run -d --rm -p 80:80 JSONvalidator:0.1`
3) `curl -s --upload-file filename.json http://localhost`
