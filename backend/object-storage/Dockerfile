FROM golang:1-alpine as builder

RUN apk --no-cache --no-progress add make git

WORKDIR /go

ENV GO111MODULE on

# Download go modules
COPY go.mod .
COPY go.sum .
RUN go mod download

COPY . .
RUN go build -o dist/objstor

FROM alpine:3.12
RUN apk update \
    && apk add --no-cache ca-certificates tzdata \
    && update-ca-certificates

COPY --from=builder /go/dist/objstor /usr/bin/objstor

ENTRYPOINT [ "/usr/bin/objstor" ]
