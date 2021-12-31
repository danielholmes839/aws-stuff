package main

import (
	"example.com/server"
	"github.com/a-h/awsapigatewayv2handler"
)

func main() {
	router := server.New("AWS Lambda")
	awsapigatewayv2handler.ListenAndServe(router)
}
