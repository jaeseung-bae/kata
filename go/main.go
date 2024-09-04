package main

import (
	"fmt"
	"net/http"

	"github.com/gin-gonic/gin"
	"github.com/jaeseung-bae/kata/go/log"
)

func main() {
	r := gin.Default()
	r.Use(log.LogMiddleware())

	r.GET("/ping", func(c *gin.Context) {
		c.JSON(http.StatusOK, gin.H{"response": "pong"})
	})

	fmt.Println("server started at :8080")
	if err := r.Run(":8080"); err != nil {
		fmt.Printf("Failed to start server with err=%v\n", err)
	}
}
