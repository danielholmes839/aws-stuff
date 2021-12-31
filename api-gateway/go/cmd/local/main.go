package main

import (
	"fmt"
	"net/http"

	"example.com/server"
)

func main() {
	router := server.New("Local version")

	if err := http.ListenAndServe("0.0.0.0:3000", router); err != nil {
		fmt.Println(err)
	}
}
