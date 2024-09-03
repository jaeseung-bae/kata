package main

import (
	"fmt"
	"net/http"

	"github.com/gin-gonic/gin"

	"git.linecorp.com/jaeseung-bae/test-gh/go/log"
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
