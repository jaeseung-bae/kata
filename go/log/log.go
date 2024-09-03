package log

import (
	"os"
	"time"

	"github.com/gin-gonic/gin"
	"github.com/sirupsen/logrus"
)

func init() {
	logrus.SetFormatter(&logrus.JSONFormatter{
		TimestampFormat: time.RFC3339,
		PrettyPrint:     true,
	})
	logrus.SetOutput(os.Stdout)
	logrus.SetLevel(logrus.TraceLevel)
}

func LogMiddleware() gin.HandlerFunc {
	return func(c *gin.Context) {
		startTime := time.Now()

		c.Next()

		duration := time.Since(startTime)
		statusCode := c.Writer.Status()
		clientIP := c.ClientIP()
		method := c.Request.Method
		path := c.Request.URL.Path

		logrus.WithFields(logrus.Fields{
			"status_code": statusCode,
			"latency":     duration,
			"client_ip":   clientIP,
			"method":      method,
			"path":        path,
		}).Info("request processed")
	}
}
