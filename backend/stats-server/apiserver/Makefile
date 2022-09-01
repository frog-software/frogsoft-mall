NAME=apiserver
VERSION=0.0.1

.PHONY: build
## build: Compile the packages.
build:
	@CGO_ENABLED=0 go build -o dist/$(NAME)

.PHONY: run
## run: Build and Run in development mode.
run: build
	@./dist/$(NAME) -e development

.PHONY: run-prod
## run-prod: Build and Run in production mode.
run-prod: build
	@./dist/$(NAME) -e production

.PHONY: clean
## clean: Clean project and previous builds.
clean:
	@rm -rf dist

.PHONY: deps
## deps: Download modules
deps:
	@go mod download